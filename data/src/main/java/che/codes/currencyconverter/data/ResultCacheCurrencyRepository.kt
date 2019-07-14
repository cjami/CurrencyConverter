package che.codes.currencyconverter.data

import che.codes.currencyconverter.data.models.Currency
import io.reactivex.Single

class ResultCacheCurrencyRepository(
    private val dataSource: CurrencyDataSource,
    private val dataStorage: CurrencyDataStorage
) : CurrencyRepository {

    override fun getCurrencies(): Single<List<Currency>> {
        return dataSource.getCurrencies().flatMap { currencies ->
            store(currencies)
        }
    }

    override fun getCachedCurrencies(): Single<List<Currency>> {
        return dataStorage.retrieve()
    }

    private fun store(currencies: List<Currency>): Single<List<Currency>> {
        return dataStorage.store(currencies)
    }
}