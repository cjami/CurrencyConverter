package che.codes.currencyconverter.features.currencylist

import che.codes.currencyconverter.data.models.Currency
import che.codes.currencyconverter.features.currencylist.CurrencyUtils.getCountryCode
import che.codes.currencyconverter.features.currencylist.CurrencyUtils.getDisplayName

data class CurrencyItem(val currency: Currency) {
    val currencyCode = currency.name
    val countryCode = getCountryCode(currencyCode)
    val displayName = getDisplayName(currencyCode)

    private var _rate = currency.rate
    var rate
        get() = _rate
        set(value) {
            _rate = value
            updateConvertedValue()
        }

    private var _baseValue = 100.0
    var baseValue
        get() = _baseValue
        set(value) {
            _baseValue = value
            updateConvertedValue()
        }

    private var _convertedValue = baseValue * currency.rate
    var convertedValue
        get() = _convertedValue
        set(value) {
            _convertedValue = value
            updateBaseValue()
        }

    private fun updateBaseValue() {
        _baseValue = _convertedValue / _rate
    }

    private fun updateConvertedValue() {
        _convertedValue = _baseValue * _rate
    }
}