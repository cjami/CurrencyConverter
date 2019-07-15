package che.codes.currencyconverter.revolutapi

import che.codes.currencyconverter.data.CurrencyDataSource
import che.codes.currencyconverter.data.models.Currency
import io.reactivex.Single

class RevolutCurrencyDataSource(private val apiService: RevolutApiService) : CurrencyDataSource {

    override fun getCurrencies(): Single<List<Currency>> {
        return apiService.getCurrencies().map { revolutData ->
            revolutData.rates.map { rate ->
                Currency(rate.key, rate.value)
            }
        }
    }
}