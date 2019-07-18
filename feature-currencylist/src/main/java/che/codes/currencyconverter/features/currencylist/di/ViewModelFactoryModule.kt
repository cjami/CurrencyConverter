package che.codes.currencyconverter.features.currencylist.di

import che.codes.currencyconverter.data.CurrencyRepository
import che.codes.currencyconverter.features.currencylist.CurrencyListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideCurrencyListViewModelFactory(repository: CurrencyRepository): CurrencyListViewModelFactory {
        return CurrencyListViewModelFactory(repository)
    }
}