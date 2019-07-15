package che.codes.currencyconverter.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import che.codes.currencyconverter.room.models.CURRENCY_TABLE_NAME
import che.codes.currencyconverter.room.models.CurrencySnapshot
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencies(currencies: List<CurrencySnapshot>): Single<List<Long>>

    @Query("SELECT * FROM $CURRENCY_TABLE_NAME")
    fun loadAllCurrencies(): Single<List<CurrencySnapshot>>
}