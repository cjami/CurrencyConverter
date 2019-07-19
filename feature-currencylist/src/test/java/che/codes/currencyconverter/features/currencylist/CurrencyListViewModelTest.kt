package che.codes.currencyconverter.features.currencylist

import androidx.lifecycle.Observer
import che.codes.currencyconverter.androidtesting.InstantTaskExecutorExtension
import che.codes.currencyconverter.androidtesting.TrampolineSchedulerExtension
import che.codes.currencyconverter.androidtesting.ViewModelUtils.invokeOnCleared
import che.codes.currencyconverter.data.CurrencyRepository
import che.codes.currencyconverter.data.models.Currency
import che.codes.currencyconverter.features.currencylist.CurrencyListViewModel.Result
import che.codes.currencyconverter.testing.FileUtils.getListFromFile
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class, TrampolineSchedulerExtension::class)
internal class CurrencyListViewModelTest {
    private lateinit var sut: CurrencyListViewModel
    private val repositoryMock: CurrencyRepository = mock()
    private val observerMock: Observer<Result> = mock()
    private val testCurrencyData: List<Currency> = getListFromFile("currencies.json", Array<Currency>::class.java)
    private val testCurrencyItems: List<CurrencyItem> = testCurrencyData.map { CurrencyItem(it) }
    private val testEur10Items: List<CurrencyItem> =
        testCurrencyData.map { CurrencyItem(it).apply { baseValue = 10.0 } }

    @BeforeEach
    fun setUp() {
        sut = CurrencyListViewModel(repositoryMock)
        sut.result.observeForever(observerMock)
    }

    @Nested
    inner class StartPolling {
        @Test
        fun `interacts with repository on success`() {
            sourceSuccess()

            sut.startPolling(0)

            verify(repositoryMock).getCurrencies()
        }

        @Test
        fun `interacts with repository cache on error`() {
            sourceError(Throwable("General Error"))
            cacheSuccess()

            sut.startPolling(0)

            verify(repositoryMock).getCachedCurrencies()
        }

        @Test
        fun `does not interact with repository cache on success`() {
            sourceSuccess()
            cacheSuccess()

            sut.startPolling(0)

            verify(repositoryMock, never()).getCachedCurrencies()
        }

        @Test
        fun `returns error on cache error`() {
            val e = Throwable("General Error")
            sourceError(e)
            cacheError(e)

            sut.startPolling(0)

            verify(observerMock).onChanged(Result.Error(e))
        }

        @Test
        fun `returns correct currencies on success`() {
            sourceSuccess()

            sut.startPolling(0)

            verify(observerMock).onChanged(Result.Success(testCurrencyItems))
        }

        @Test
        fun `returns correct currencies on error but cache success`() {
            sourceError(Throwable("General Error"))
            cacheSuccess()

            sut.startPolling(0)

            verify(observerMock).onChanged(Result.Success(testCurrencyItems))
        }
    }

    @Nested
    inner class StopPolling {
        @Test
        fun `clears disposables`() {
            sut.stopPolling()

            assertThat(sut.disposables.size(), equalTo(0))
        }
    }

    @Nested
    inner class ChangeCurrencyItem {
        @Test
        fun `updates other currency items correctly`() {
            sourceSuccess()

            sut.startPolling(0)
            sut.changeCurrencyItem("EUR", 10.0)

            verify(observerMock).onChanged(Result.Success(testEur10Items))
        }
    }

    @Nested
    inner class OnCleared {
        @Test
        fun `when cleared disposables are cleared`() {
            invokeOnCleared(sut)

            assertThat(sut.disposables.size(), equalTo(0))
        }
    }

    //region Helper Methods

    private fun sourceSuccess() {
        whenever(repositoryMock.getCurrencies()).thenReturn(Single.just(testCurrencyData))
    }

    private fun sourceError(e: Throwable) {
        whenever(repositoryMock.getCurrencies()).thenReturn(Single.error(e))
    }

    private fun cacheSuccess() {
        whenever(repositoryMock.getCachedCurrencies()).thenReturn(Single.just(testCurrencyData))
    }

    private fun cacheError(e: Throwable) {
        whenever(repositoryMock.getCachedCurrencies()).thenReturn(Single.error(e))
    }

    //endregion
}