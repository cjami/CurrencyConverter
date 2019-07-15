package che.codes.currencyconverter.features.currencylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import che.codes.currencyconverter.features.currencylist.CurrencyListAdapter.RowData
import che.codes.currencyconverter.features.currencylist.CurrencyListAdapter.ViewHolder.ItemViewHolder
import com.mikhaellopez.hfrecyclerviewkotlin.HFRecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.row_currency.view.*
import java.text.NumberFormat

class CurrencyListAdapter(private val baseFlagUrl: String) :
    HFRecyclerView<RowData>(withHeader = false, withFooter = false) {

    private val valueEditSubject = PublishSubject.create<Double>()
    val valueEditEvent: Observable<Double> = valueEditSubject

    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        class ItemViewHolder(
            private val view: View,
            private val baseFlagUrl: String,
            private val valueEditObserver: Observer<Double>
        ) : ViewHolder(view) {

            init {
                view.currency_display_value.doAfterTextChanged {
                    valueEditObserver.onNext(view.currency_display_value.text.toString().toDouble())
                }
            }

            fun bind(rowData: RowData) {
                view.currency_code.text = rowData.currencyCode
                view.currency_display_name.text = rowData.displayName
                view.currency_display_value.setText(rowData.displayValue)

                Picasso.get()
                    .load("$baseFlagUrl/${rowData.countryCode}/shiny/64.png")
                    .into(view.currency_icon)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    override fun getFooterView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        return ItemViewHolder(inflater.inflate(R.layout.row_currency, parent, false), baseFlagUrl, valueEditSubject)
    }

    fun update(currencyItems: List<CurrencyItem>) {
        if (data.isEmpty()) {
            data = currencyItems.map { RowData(it) }
        } else {
            currencyItems.forEach { item ->
                val rowData = data.find { it.currencyCode == item.currencyCode }
                rowData?.displayValue = rowData?.formatter?.format(item.convertedValue) ?: ""
            }
        }

        notifyDataSetChanged()
    }

    class RowData(item: CurrencyItem) {
        val currencyCode: String = item.currencyCode
        val countryCode: String = item.countryCode
        val displayName: String = item.displayName
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        var displayValue: String = formatter.format(item.convertedValue)
    }
}