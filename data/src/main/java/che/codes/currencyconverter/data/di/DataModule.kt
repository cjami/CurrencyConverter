package che.codes.currencyconverter.data.di

import che.codes.currencyconverter.data.CurrencyDataSource
import che.codes.currencyconverter.data.CurrencyDataStorage
import che.codes.currencyconverter.data.CurrencyRepository
import che.codes.currencyconverter.data.ResultCacheCurrencyRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideCurrencyRepository(source: CurrencyDataSource, storage: CurrencyDataStorage): CurrencyRepository {
        return ResultCacheCurrencyRepository(source, storage)
    }
}