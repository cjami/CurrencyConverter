package che.codes.currencyconverter

import android.app.Application
import che.codes.currencyconverter.core.di.AppContextModule
import che.codes.currencyconverter.core.di.CoreComponent
import che.codes.currencyconverter.core.di.DaggerCoreComponent

class CurrencyConverterApplication : Application(), CoreComponent.Provider {

    private var coreComponent: CoreComponent? = null

    override fun setCoreComponent(coreComponent: CoreComponent) {
        this.coreComponent = coreComponent
    }

    override fun provideCoreComponent(): CoreComponent {
        if (coreComponent == null) {
            coreComponent = DaggerCoreComponent.builder().appContextModule(AppContextModule(this)).build()
        }
        return coreComponent!!
    }
}