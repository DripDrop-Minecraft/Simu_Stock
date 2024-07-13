package games.dripdrop.simustock.utils

import java.text.SimpleDateFormat
import java.util.*

object UniqueIDManager {

    fun createStockCode(): String {
        return StringBuilder(UUID.randomUUID().toString().substring(0, 4).uppercase())
            .append(SimpleDateFormat("yyMMddHHmm", Locale.PRC).format(Date()))
            .append("DD")
            .toString()
    }

    fun createOrderNumber(): String {
        return StringBuilder("ON")
            .append(SimpleDateFormat("yyMMddHHmmssSSS", Locale.PRC).format(Date()))
            .append("U")
            .append(UUID.randomUUID().toString().substring(0, 4).uppercase())
            .toString()
    }

    inline fun <reified T> createTableName(): String = "simustock_${T::class.java.simpleName.lowercase()}"
}