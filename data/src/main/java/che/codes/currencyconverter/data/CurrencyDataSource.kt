package che.codes.currencyconverter.data

import che.codes.currencyconverter.data.models.Currency
import io.reactivex.Single

interface CurrencyDataSource {
    fun getCurrencies(): Single<List<Currency>>
}