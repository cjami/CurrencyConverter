package che.codes.currencyconverter

import android.app.Application
import che.codes.currencyconverter.core.di.AppContextModule
import che.codes.currencyconverter.core.di.CoreComponent
import che.codes.currencyconverter.core.di.DaggerCoreComponent

class CurrencyConverterApplication : Application(), CoreComponent.Provider {

    override fun provideCoreComponent(): CoreComponent {
        return DaggerCoreComponent.builder().appContextModule(AppContextModule(this)).build()
    }

}