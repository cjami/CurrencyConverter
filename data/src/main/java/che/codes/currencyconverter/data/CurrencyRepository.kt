package che.codes.currencyconverter.data

import che.codes.currencyconverter.data.models.Currency
import io.reactivex.Single

interface CurrencyRepository {
    fun getCurrencies(): Single<List<Currency>>
    fun getCachedCurrencies(): Single<List<Currency>>
}