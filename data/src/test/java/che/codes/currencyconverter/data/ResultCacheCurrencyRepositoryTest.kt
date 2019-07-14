package che.codes.currencyconverter.data

import che.codes.currencyconverter.data.models.Currency
import che.codes.currencyconverter.testing.FileUtils.getListFromFile
import che.codes.currencyconverter.testing.RxTestUtils.assertError
import che.codes.currencyconverter.testing.RxTestUtils.getResult
import che.codes.currencyconverter.testing.RxTestUtils.waitForTerminalEvent
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ResultCacheCurrencyRepositoryTest {
    private lateinit var sut: ResultCacheCurrencyRepository
    private val dataSourceMock: CurrencyDataSource = mock()
    private val dataStorageMock: CurrencyDataStorage = mock()
    private val testData: List<Currency> = getListFromFile("currencies.json", Array<Currency>::class.java)

    @BeforeEach
    internal fun setUp() {
        sut = ResultCacheCurrencyRepository(dataSourceMock, dataStorageMock)
    }

    @Nested
    inner class GetCurrencies {
        @Test
        fun `interacts with source`() {
            sourceSuccess()

            waitForTerminalEvent(sut.getCurrencies())

            verify(dataSourceMock).getCurrencies()
        }

        @Test
        fun `interacts with storage`() {
            sourceSuccess()
            storageStoreSuccess()

            waitForTerminalEvent(sut.getCurrencies())

            verify(dataStorageMock).store(any())
        }

        @Test
        fun `stores correct currencies on source success`() {
            sourceSuccess()
            storageStoreSuccess()

            waitForTerminalEvent(sut.getCurrencies())

            verify(dataStorageMock).store(testData)
        }

        @Test
        fun `returns correct currencies on source success`() {
            sourceSuccess()
            storageStoreSuccess()

            val result = getResult(sut.getCurrencies())

            assertThat(result, equalTo(testData))
        }

        @Test
        fun `does not store currencies on source error`() {
            val e = Throwable("General Error")
            sourceError(e)

            waitForTerminalEvent(sut.getCurrencies())

            verifyZeroInteractions(dataStorageMock)
        }

        @Test
        fun `returns error on source error`() {
            val e = Throwable("General Error")
            sourceError(e)

            assertError(sut.getCurrencies(), e)
        }

        @Test
        fun `returns error on storage store error`() {
            val e = Throwable("General Error")
            sourceSuccess()
            storageStoreError(e)

            assertError(sut.getCurrencies(), e)
        }
    }

    @Nested
    inner class GetCachedCurrencies {
        @Test
        fun `interacts with storage`() {
            storageRetrieveSuccess()

            waitForTerminalEvent(sut.getCachedCurrencies())

            verify(dataStorageMock).retrieve()
        }

        @Test
        fun `does not interact with source`() {
            sourceSuccess()
            storageRetrieveSuccess()

            waitForTerminalEvent(sut.getCachedCurrencies())

            verifyZeroInteractions(dataSourceMock)
        }

        @Test
        fun `returns correct currencies on storage retrieve success`() {
            storageRetrieveSuccess()

            val result = getResult(sut.getCachedCurrencies())

            assertThat(result, equalTo(testData))
        }

        @Test
        internal fun `returns error on storage retrieve error`() {
            val e = Throwable("General Error")
            storageRetrieveError(e)

            assertError(sut.getCachedCurrencies(), e)
        }
    }

    //region Helper Methods

    private fun sourceSuccess() {
        whenever(dataSourceMock.getCurrencies()).thenReturn(Single.just(testData))
    }

    private fun sourceError(e: Throwable) {
        whenever(dataSourceMock.getCurrencies()).thenReturn(Single.error(e))
    }

    private fun storageStoreSuccess() {
        whenever(dataStorageMock.store(any())).thenReturn(Single.just(testData))
    }

    private fun storageRetrieveSuccess() {
        whenever(dataStorageMock.retrieve()).thenReturn(Single.just(testData))
    }

    private fun storageStoreError(e: Throwable) {
        whenever(dataStorageMock.store(any())).thenReturn(Single.error(e))
    }

    private fun storageRetrieveError(e: Throwable) {
        whenever(dataStorageMock.retrieve()).thenReturn(Single.error(e))
    }

    //endregion
}