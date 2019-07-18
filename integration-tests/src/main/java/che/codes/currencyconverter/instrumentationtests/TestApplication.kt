package che.codes.currencyconverter.instrumentationtests

import android.app.Application
import che.codes.currencyconverter.core.di.AppContextModule
import che.codes.currencyconverter.core.di.CoreComponent
import che.codes.currencyconverter.core.di.DaggerCoreComponent

class TestApplication: Application(), CoreComponent.Provider {

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