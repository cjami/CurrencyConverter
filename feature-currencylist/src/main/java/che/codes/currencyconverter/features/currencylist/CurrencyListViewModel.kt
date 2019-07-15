package che.codes.currencyconverter.features.currencylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import che.codes.currencyconverter.data.CurrencyRepository
import che.codes.currencyconverter.data.models.Currency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CurrencyListViewModel(private val repository: CurrencyRepository) : ViewModel() {

    val result = MutableLiveData<Result>()
    val disposables = CompositeDisposable()

    private var currencyItems: List<CurrencyItem> = emptyList()

    fun getCurrencies() {
        disposables.add(
            repository.getCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    result.value = Result.Success(updateCurrencyItems(data))
                }, {
                    // If source fails to provide currencies, get cached copies instead
                    getCachedCurrencies()
                })
        )
    }

    fun changeCurrencyItem(currencyCode: String, convertedValue: Double) {
        val item = findCurrencyItem(currencyCode)
        item?.apply {
            this.convertedValue = convertedValue

            // Update all base values of other currencies to new base value
            currencyItems.forEach {
                if (it != this) {
                    it.baseValue = this.baseValue
                }
            }
        }
    }

    private fun getCachedCurrencies() {
        disposables.add(
            repository.getCachedCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    result.value = Result.Success(updateCurrencyItems(data))
                },
                    { error ->
                        result.value = Result.Error(error)
                    })
        )
    }

    private fun updateCurrencyItems(currencies: List<Currency>): List<CurrencyItem> {
        if (currencyItems.isEmpty()) {
            currencyItems = currencies.map {
                CurrencyItem(it)
            }
        } else {
            currencies.forEach { currency ->
                val item = findCurrencyItem(currency.name)
                item?.rate = currency.rate
            }
        }

        return currencyItems
    }

    private fun findCurrencyItem(currencyCode: String): CurrencyItem? {
        return currencyItems.find { it.currencyCode == currencyCode }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    sealed class Result {
        data class Success(val currencies: List<CurrencyItem>) : Result()
        data class Error(val exception: Throwable) : Result()
    }
}