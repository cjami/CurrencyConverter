package che.codes.currencyconverter.revolutapi

import che.codes.currencyconverter.data.models.Currency
import che.codes.currencyconverter.revolutapi.models.RevolutCurrencyData
import che.codes.currencyconverter.testing.FileUtils.getListFromFile
import che.codes.currencyconverter.testing.FileUtils.getObjectFromFile
import che.codes.currencyconverter.testing.RxTestUtils.assertError
import che.codes.currencyconverter.testing.RxTestUtils.getResult
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class RevolutCurrencyDataSourceTest {
    private lateinit var sut: RevolutCurrencyDataSource
    private val apiServiceMock: RevolutApiService = mock()
    private val testPayload = getObjectFromFile("revolut_payload.json", RevolutCurrencyData::class.java)
    private val expectedData = getListFromFile("currencies.json", Array<Currency>::class.java)

    @BeforeEach
    fun setUp() {
        sut = RevolutCurrencyDataSource(apiServiceMock)
    }

    @Nested
    inner class GetCurrencies {
        @Test
        fun `interacts with service`() {
            success()

            sut.getCurrencies()

            verify(apiServiceMock).getCurrencies()
        }

        @Test
        fun `returns correct currencies on service success`() {
            success()

            val result = getResult(sut.getCurrencies())

            assertThat(result, equalTo(expectedData))
        }

        @Test
        fun `returns error on service error`() {
            val e = Throwable("General Error")
            error(e)

            assertError(sut.getCurrencies(), e)
        }
    }

    //region Helper Methods

    private fun success() {
        whenever(apiServiceMock.getCurrencies()).thenReturn(Single.just(testPayload))
    }

    private fun error(e: Throwable) {
        whenever(apiServiceMock.getCurrencies()).thenReturn(Single.error(e))
    }

    //endregion
}