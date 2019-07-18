package che.codes.currencyconverter.room.di

import android.content.Context
import androidx.room.Room
import che.codes.currencyconverter.data.CurrencyDataStorage
import che.codes.currencyconverter.room.CurrencyDatabase
import che.codes.currencyconverter.room.RoomCurrencyDataStorage
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    fun provideRoomCurrencyDataStorage(appContext: Context): CurrencyDataStorage {
        val db = Room.databaseBuilder(appContext, CurrencyDatabase::class.java, "currency").build()

        return RoomCurrencyDataStorage(db)
    }
}