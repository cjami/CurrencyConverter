package che.codes.currencyconverter.features.currencylist

import che.codes.currencyconverter.data.models.Currency
import che.codes.currencyconverter.features.currencylist.CurrencyUtils.getCountryCode
import che.codes.currencyconverter.features.currencylist.CurrencyUtils.getDisplayName

data class CurrencyItem(val currency: Currency) {
    val currencyCode = currency.name
    val countryCode = getCountryCode(currencyCode)
    val displayName = getDisplayName(currencyCode)

    private var _rate = currency.rate
    var rate = 1.0
        get() = _rate
        set(value) {
            field = value
            updateConvertedValue()
        }

    private var _baseValue = 1.0
    var baseValue = 1.0
        get() = _baseValue
        set(value) {
            field = value
            updateConvertedValue()
        }

    private var _convertedValue = baseValue * currency.rate
    var convertedValue = 1.0
        get() = _convertedValue
        set(value) {
            field = value
            updateBaseValue()
        }

    private fun updateBaseValue() {
        _baseValue = _convertedValue / _rate
    }

    private fun updateConvertedValue() {
        _convertedValue = _baseValue * _rate
    }
}