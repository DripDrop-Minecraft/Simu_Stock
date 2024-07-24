package games.dripdrop.simustock.presenter.network

import com.google.gson.JsonObject
import games.dripdrop.simustock.model.bean.RealStockIndex
import games.dripdrop.simustock.model.constants.Region
import games.dripdrop.simustock.presenter.utils.JsonManager.toObject
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitManager {
    private const val BASE_URL = "https://proxy.finance.qq.com/ifzqgtimg/appstock/app/rank/"
    private val mRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 获取现实世界股票指数
    fun getRealStockIndexes(callback: (List<RealStockIndex?>) -> Unit) {
        val list = arrayListOf<RealStockIndex?>()
        fun JsonObject.storeData(region: Region) {
            list.addAll(getAsJsonArray(region.lowerName).map { element ->
                element.asJsonObject.toObject<RealStockIndex>()
            })
        }
        createService().getStockIndexByMarket().enqueue(object : Callback<JsonObject> {
            override fun onResponse(p0: Call<JsonObject>, p1: Response<JsonObject>) {
                p1.body()?.getAsJsonObject("data")?.let {
                    arrayOf(Region.AMERICA, Region.EUROPE, Region.ASIA, Region.OTHER).onEach { r -> it.storeData(r) }
                    callback(list)
                }
            }

            override fun onFailure(p0: Call<JsonObject>, p1: Throwable) {
                PluginLogManager.e("failed to get real stock index: ${p1.localizedMessage}")
                p1.printStackTrace()
            }

        })
    }

    private fun createService(): IRetrofitApiService = mRetrofit.create(IRetrofitApiService::class.java)
}