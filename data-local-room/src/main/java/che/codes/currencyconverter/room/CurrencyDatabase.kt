package che.codes.currencyconverter.room

import androidx.room.Database
import androidx.room.RoomDatabase
import che.codes.currencyconverter.room.models.CurrencySnapshot

@Database(entities = arrayOf(CurrencySnapshot::class), version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}