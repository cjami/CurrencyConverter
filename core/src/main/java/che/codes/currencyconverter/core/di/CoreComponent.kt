package che.codes.currencyconverter.core.di

import android.content.Context
import che.codes.currencyconverter.data.CurrencyRepository
import che.codes.currencyconverter.data.di.DataModule
import che.codes.currencyconverter.revolutapi.di.RevolutApiModule
import che.codes.currencyconverter.room.di.RoomModule
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Component(
    modules = [
        AppContextModule::class,
        NetworkModule::class,
        PropertyModule::class,
        DataModule::class,
        RevolutApiModule::class,
        RoomModule::class
    ]
)
interface CoreComponent {
    @Singleton fun provideOkHttpClient(): OkHttpClient
    @Singleton fun provideCurrencyRepository(): CurrencyRepository
    @Singleton fun provideAppContext(): Context

    interface Provider {
        fun provideCoreComponent(): CoreComponent
    }

    companion object {
        fun getInstance(applicationContext: Context): CoreComponent {
            return (applicationContext as Provider).provideCoreComponent()
        }
    }
}