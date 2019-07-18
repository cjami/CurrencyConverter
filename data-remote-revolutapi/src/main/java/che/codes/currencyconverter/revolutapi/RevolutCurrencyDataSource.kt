package che.codes.currencyconverter.revolutapi

import che.codes.currencyconverter.data.CurrencyDataSource
import che.codes.currencyconverter.data.models.Currency
import io.reactivex.Single

class RevolutCurrencyDataSource(private val apiService: RevolutApiService) : CurrencyDataSource {

    override fun getCurrencies(): Single<List<Currency>> {
        return apiService.getCurrencies().map { revolutData ->
            val revolutDataWithBase = revolutData.rates.map { rate ->
                Currency(rate.key, rate.value)
            }.toMutableList()

            // Add Base Currency to list
            revolutDataWithBase.add(Currency("EUR", 1.0))

            revolutDataWithBase.sortedBy { it.name }.toList()
        }
    }
}