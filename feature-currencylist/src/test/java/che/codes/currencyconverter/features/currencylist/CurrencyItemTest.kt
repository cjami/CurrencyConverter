package che.codes.currencyconverter.features.currencylist

import che.codes.currencyconverter.data.models.Currency
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CurrencyItemTest {

    private lateinit var sut : CurrencyItem

    @BeforeEach
    fun setUp() {
        sut = CurrencyItem(Currency("GBP", 2.0))
    }

    @Test
    fun `changes converted value when base value changes`() {
        sut.baseValue = 10.0

        assertThat(sut.convertedValue, equalTo(20.0))
    }

    @Test
    fun `changes base value when converted value changes`() {
        sut.convertedValue = 30.0

        assertThat(sut.baseValue, equalTo(15.0))
    }

    @Test
    fun `changes converted value when rate changes`() {
        sut.baseValue = 2.0

        sut.rate = 5.0

        assertThat(sut.convertedValue, equalTo(10.0))
    }

    @Test
    fun `does not change base value when rate changes`() {
        sut.baseValue = 32.0

        sut.rate = 2.0

        assertThat(sut.baseValue, equalTo(32.0))
    }
}