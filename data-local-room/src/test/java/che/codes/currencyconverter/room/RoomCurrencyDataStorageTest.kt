package che.codes.currencyconverter.room

import che.codes.currencyconverter.data.models.Currency
import che.codes.currencyconverter.room.models.CurrencySnapshot
import che.codes.currencyconverter.testing.FileUtils.getListFromFile
import che.codes.currencyconverter.testing.RxTestUtils.assertError
import che.codes.currencyconverter.testing.RxTestUtils.getResult
import che.codes.currencyconverter.testing.RxTestUtils.waitForTerminalEvent
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class RoomCurrencyDataStorageTest {
    private lateinit var sut: RoomCurrencyDataStorage
    private val databaseMock: CurrencyDatabase = mock()
    private val daoMock: CurrencyDao = mock()
    private val testCurrencies = getListFromFile("currencies.json", Array<Currency>::class.java)
    private val testSnapshots = testCurrencies.map { CurrencySnapshot(it.name, it.rate) }

    @BeforeEach
    fun setUp() {
        whenever(databaseMock.currencyDao()).thenReturn(daoMock)

        sut = RoomCurrencyDataStorage(databaseMock)
    }

    @Nested
    inner class Store {
        @Test
        fun `interacts with dao`() {
            storeSuccess()

            waitForTerminalEvent(sut.store(testCurrencies))

            verify(daoMock).insertCurrencies(testSnapshots)
        }

        @Test
        fun `returns correct currencies on store success`() {
            storeSuccess()

            val result = getResult(sut.store(testCurrencies))

            assertThat(result, equalTo(testCurrencies))
        }

        @Test
        fun `returns error on store error`() {
            val e = Throwable("General Error")
            storeError(e)

            assertError(sut.store(testCurrencies), e)
        }
    }

    @Nested
    inner class Retrieve {
        @Test
        fun `interacts with dao`() {
            retrieveSuccess()

            waitForTerminalEvent(sut.retrieve())

            verify(daoMock).loadAllCurrencies()
        }

        @Test
        fun `returns correct currencies on retrieve success`() {
            retrieveSuccess()

            val result = getResult(sut.retrieve())

            assertThat(result, equalTo(testCurrencies))
        }

        @Test
        fun `returns error on retrieve error`() {
            val e = Throwable("General Error")
            retrieveError(e)

            assertError(sut.retrieve(), e)
        }
    }

    //region Helper Methods

    private fun storeSuccess() {
        whenever(daoMock.insertCurrencies(any())).thenReturn(Single.just(listOf(1L, 2L, 3L)))
    }

    private fun storeError(e: Throwable) {
        whenever(daoMock.insertCurrencies(any())).thenReturn(Single.error(e))
    }

    private fun retrieveSuccess() {
        whenever(daoMock.loadAllCurrencies()).thenReturn(Single.just(testSnapshots))
    }

    private fun retrieveError(e: Throwable) {
        whenever(daoMock.loadAllCurrencies()).thenReturn(Single.error(e))
    }

    //endregion
}