package che.codes.currencyconverter.features.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import che.codes.currencyconverter.core.di.CoreComponent
import che.codes.currencyconverter.features.currencylist.CurrencyListAdapter.ValueEditEvent
import che.codes.currencyconverter.features.currencylist.CurrencyListViewModel.Result
import che.codes.currencyconverter.features.currencylist.di.DaggerCurrencyListComponent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_currency_list.*
import javax.inject.Inject

class CurrencyListFragment : Fragment() {

    private lateinit var currencyAdapter: CurrencyListAdapter

    @Inject
    lateinit var viewModelFactory: CurrencyListViewModelFactory

    private lateinit var viewModel: CurrencyListViewModel

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerCurrencyListComponent.builder()
            .coreComponent(CoreComponent.getInstance(activity!!.applicationContext))
            .build().inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyAdapter = CurrencyListAdapter()
        disposables.add(currencyAdapter.valueEditEvent.subscribe { handleValueEditEvent(it) })

        currency_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = currencyAdapter
        }

        viewModel.getCurrencies()
        viewModel.result.observe(this, Observer<Result> { handleResult(it) })
    }

    private fun handleResult(result: Result) {
        when (result) {
            is Result.Success -> {
                currencyAdapter.update(result.currencies)
            }
            is Result.Error -> {
                //TODO: Handle Errors
            }
        }
    }

    private fun handleValueEditEvent(event: ValueEditEvent) {
        viewModel.changeCurrencyItem(event.currencyCode, event.convertedValue)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        disposables.clear()
    }

}