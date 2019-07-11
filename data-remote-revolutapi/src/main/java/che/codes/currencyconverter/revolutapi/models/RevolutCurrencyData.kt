package che.codes.currencyconverter.revolutapi.models

import java.util.*

data class RevolutCurrencyData(
    val base: String,
    val date: Date,
    val rates: Map<String, Double>
)