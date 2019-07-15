package che.codes.currencyconverter.room.di

import android.app.Application
import androidx.room.Room
import che.codes.currencyconverter.room.CurrencyDatabase
import che.codes.currencyconverter.room.RoomCurrencyDataStorage
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    fun provideRoomCurrencyDataStorage(app: Application): RoomCurrencyDataStorage {
        val db = Room.databaseBuilder(app, CurrencyDatabase::class.java, "currency").build()

        return RoomCurrencyDataStorage(db)
    }
}