package che.codes.currencyconverter.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENCY_TABLE_NAME = "currency"

@Entity(tableName = CURRENCY_TABLE_NAME)
data class CurrencySnapshot(
    @PrimaryKey
    val name: String,
    val rate: Double
)