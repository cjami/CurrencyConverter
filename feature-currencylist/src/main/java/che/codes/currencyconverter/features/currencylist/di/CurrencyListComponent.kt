package che.codes.currencyconverter.features.currencylist.di

import che.codes.currencyconverter.core.di.CoreComponent
import che.codes.currencyconverter.features.currencylist.CurrencyListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [CoreComponent::class], modules = [ViewModelFactoryModule::class])
interface CurrencyListComponent {

    fun inject(fragment: CurrencyListFragment)

    @Component.Builder
    interface Builder {
        fun build(): CurrencyListComponent
        fun coreComponent(component: CoreComponent): Builder
    }
}