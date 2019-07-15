package che.codes.currencyconverter.features.currencylist

import java.util.*

object CurrencyUtils {

    fun getDisplayName(currencyCode: String): String {
        return Currency.getInstance(currencyCode).displayName
    }

    fun getCountryCode(currencyCode: String): String {
        return when (currencyCode.isNotEmpty()) {
            true -> currencyCode.dropLast(1)
            false -> ""
        }
    }
}