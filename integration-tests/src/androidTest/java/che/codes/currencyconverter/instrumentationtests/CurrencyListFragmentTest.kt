package che.codes.currencyconverter.instrumentationtests

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import che.codes.currencyconverter.androidtesting.IdlingSchedulerRule
import che.codes.currencyconverter.androidtesting.MatcherUtils.hasRecyclerItemCount
import che.codes.currencyconverter.core.di.AppContextModule
import che.codes.currencyconverter.core.di.CoreComponent
import che.codes.currencyconverter.core.di.NetworkModule
import che.codes.currencyconverter.data.di.DataModule
import che.codes.currencyconverter.features.currencylist.CurrencyListFragment
import che.codes.currencyconverter.revolutapi.di.RevolutApiModule
import che.codes.currencyconverter.room.di.RoomModule
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Named
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
class CurrencyListFragmentTest {

    @get:Rule
    val schedulerRule = IdlingSchedulerRule()

    private lateinit var mockServer: MockWebServer

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

        val component = DaggerCurrencyListFragmentTest_TestCoreComponent.builder()
            .appContextModule(AppContextModule(appContext))
            .testPropertyModule(TestPropertyModule(mockServer.url("").toString()))
            .build()

        (appContext as CoreComponent.Provider).setCoreComponent(component)
    }

    @Test
    fun afterFetching_onSuccess_currenciesListed() {
        success()

        launchFragmentInContainer<CurrencyListFragment>()

        onView(withId(R.id.currency_list)).check(matches(hasRecyclerItemCount(4)))
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    //region Helper methods and classes

    private fun success() {
        mockServer.enqueue(createJsonResponse(200, getJsonFromFile("success_response.json")))
    }

    private fun createJsonResponse(code: Int, body: String = "{}"): MockResponse {
        return MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody(body)
            .setResponseCode(code)
    }

    private fun getJsonFromFile(filename: String): String {
        val inputStream = InstrumentationRegistry.getInstrumentation().context.assets.open(filename)
        return inputStream.bufferedReader().use { it.readText() }
    }

    @Singleton
    @Component(
        modules = [
            AppContextModule::class,
            NetworkModule::class,
            TestPropertyModule::class,
            DataModule::class,
            RevolutApiModule::class,
            RoomModule::class
        ]
    )
    interface TestCoreComponent : CoreComponent

    @Module
    class TestPropertyModule(private val baseApiUrl: String) {

        @Provides
        @Named("base.api.url")
        fun provideBaseUrl(): String {
            return baseApiUrl
        }
    }

    //endregion
}