package che.codes.currencyconverter.room

import che.codes.currencyconverter.data.CurrencyDataStorage
import che.codes.currencyconverter.data.models.Currency
import che.codes.currencyconverter.room.models.CurrencySnapshot
import io.reactivex.Single

class RoomCurrencyDataStorage(database: CurrencyDatabase) : CurrencyDataStorage {
    private val dao = database.currencyDao()

    override fun store(currencies: List<Currency>): Single<List<Currency>> {
        return dao.insertCurrencies(currencies.map { CurrencySnapshot(it.name, it.rate) }).map { currencies }
    }

    override fun retrieve(): Single<List<Currency>> {
        return dao.loadAllCurrencies().map { snapshots -> snapshots.map { Currency(it.name, it.rate) } }
    }

}