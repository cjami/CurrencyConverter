package che.codes.currencyconverter.core.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppContextModule(val appContext: Context) {

    @Provides
    fun provideContext(): Context {
        return appContext
    }
}