package che.codes.currencyconverter.revolutapi.di

import che.codes.currencyconverter.data.CurrencyDataSource
import che.codes.currencyconverter.revolutapi.RevolutApiService
import che.codes.currencyconverter.revolutapi.RevolutCurrencyDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class RevolutApiModule {

    @Provides
    fun provideRevolutCurrencyDataSource(
        okHttpClient: OkHttpClient,
        @Named("base.api.url") baseUrl: String
    ): CurrencyDataSource {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return RevolutCurrencyDataSource(retrofit.create(RevolutApiService::class.java))
    }
}