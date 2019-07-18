package che.codes.currencyconverter.di

import che.codes.currencyconverter.MainActivity
import che.codes.currencyconverter.core.di.CoreComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [CoreComponent::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}