package che.codes.currencyconverter.revolutapi

import che.codes.currencyconverter.revolutapi.models.RevolutCurrencyData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutApiService {
    @GET("latest")
    fun getCurrencies(@Query("base") base: String = "EUR"): Single<RevolutCurrencyData>
}