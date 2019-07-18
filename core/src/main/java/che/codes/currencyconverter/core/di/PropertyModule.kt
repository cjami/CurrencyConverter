package che.codes.currencyconverter.core.di

import dagger.Module
import dagger.Provides
import javax.inject.Named

private const val BASE_API_URL = "https://revolut.duckdns.org/"

@Module
class PropertyModule {

    @Provides
    @Named("base.api.url")
    fun provideBaseApiUrl(): String {
        return BASE_API_URL
    }
}