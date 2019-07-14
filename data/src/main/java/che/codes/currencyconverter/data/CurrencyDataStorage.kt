package che.codes.currencyconverter.data

import che.codes.currencyconverter.data.models.Currency
import io.reactivex.Single

interface CurrencyDataStorage {
    fun store(currencies: List<Currency>) : Single<List<Currency>>
    fun retrieve(): Single<List<Currency>>
}