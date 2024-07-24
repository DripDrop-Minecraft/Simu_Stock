package games.dripdrop.simustock.presenter.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface IRetrofitApiService {
    @GET("indexRankDetail2?")
    fun getStockIndexByMarket(): Call<JsonObject>
}