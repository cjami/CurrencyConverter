package che.codes.currencyconverter.features.currencylist

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import che.codes.currencyconverter.core.ui.util.EditTextUtils.setCursorToEnd
import che.codes.currencyconverter.core.ui.util.KeyboardUtils
import che.codes.currencyconverter.features.currencylist.CurrencyListAdapter.ViewHolder
import che.codes.currencyconverter.features.currencylist.CurrencyUtils.getLocalCurrencyCode
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.row_currency.view.*
import java.text.NumberFormat

class CurrencyListAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val data: MutableList<RowData> = mutableListOf()
    private val valueEditSubject = PublishSubject.create<ValueEditEvent>()
    private lateinit var recyclerView: RecyclerView
    val valueEditEvent: Observable<ValueEditEvent> = valueEditSubject

    inner class ViewHolder(
        private val view: View,
        private val valueEditObserver: Observer<ValueEditEvent>
    ) : RecyclerView.ViewHolder(view) {
        private lateinit var rowData: RowData
        private var textWatcher: TextWatcher

        init {
            // Emit an edit event when user edits the value
            textWatcher = view.currency_display_value.doAfterTextChanged { text ->
                var newValue = 0.0
                if (text != null && text.isNotEmpty()) {
                    newValue = rowData.formatter.parse(text.toString()).toDouble()
                }

                valueEditObserver.onNext(ValueEditEvent(rowData.currencyCode, newValue))
                rowData.displayValue = rowData.formatter.format(newValue.toLong())

                syncDisplayValue()
            }

            // Click row when value is touched
            view.currency_display_value.setOnTouchListener { _, _ ->
                view.callOnClick()
                setCursorToEnd(view.currency_display_value)
                true
            }

            // Hide keyboard when row is touched
            view.setOnTouchListener { v, _ ->
                KeyboardUtils.hideSoftKeyboard(v)
                false
            }

            // Hide keyboard on editor action
            view.currency_display_value.setOnEditorActionListener { v, _, _ ->
                KeyboardUtils.hideSoftKeyboard(v)
                true // Consume action
            }

            // Move row to top when user clicks on it
            view.setOnClickListener {
                moveTop()
                view.currency_display_value.apply {
                    requestFocus()
                    KeyboardUtils.showSoftKeyboard(this)
                    setCursorToEnd(this)
                }
            }
        }

        fun bind(rowData: RowData) {
            this.rowData = rowData

            view.currency_code.text = rowData.currencyCode
            view.currency_display_name.text = rowData.displayName

            syncDisplayValue()

            Picasso.get()
                .load("https://www.countryflags.io/${rowData.countryCode}/shiny/64.png")
                .into(view.currency_icon)
        }

        private fun moveTop() {
            if (adapterPosition > 0) {
                data.removeAt(adapterPosition)
                data.add(0, rowData)

                notifyItemMoved(adapterPosition, 0)

                recyclerView.scrollToPosition(0)
            }
        }

        private fun syncDisplayValue(){
            view.currency_display_value.apply {
                removeTextChangedListener(textWatcher)
                setText(rowData.displayValue)
                addTextChangedListener(textWatcher)
                setCursorToEnd(this)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.row_currency, parent, false)
        return ViewHolder(layout, valueEditSubject)
    }

    override fun getItemCount(): Int = data.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    fun update(currencyItems: List<CurrencyItem>) {
        if (data.isEmpty()) {
            data.addAll(currencyItems.map { RowData(it) })

            // Place local currency on top
            data.find { it.currencyCode == getLocalCurrencyCode() }?.let {
                data.remove(it)
                data.add(0, it)
            }

            notifyDataSetChanged()
        } else {
            data.forEachIndexed { i, rowData ->
                if (i > 0) { // Top row is untouched
                    currencyItems.find { it.currencyCode == rowData.currencyCode }?.let {
                        val newDisplayValue = rowData.formatter.format(it.convertedValue.toLong()) ?: "0"

                        if (rowData.displayValue != newDisplayValue) {
                            rowData.displayValue = newDisplayValue
                            notifyItemChanged(i)
                        }
                    }
                }
            }
        }
    }

    class RowData(item: CurrencyItem) {
        val currencyCode: String = item.currencyCode
        val countryCode: String = item.countryCode
        val displayName: String = item.displayName
        val formatter: NumberFormat = NumberFormat.getInstance()
        var displayValue: String = formatter.format(item.convertedValue.toLong())
    }

    data class ValueEditEvent(val currencyCode: String, val convertedValue: Double)
}