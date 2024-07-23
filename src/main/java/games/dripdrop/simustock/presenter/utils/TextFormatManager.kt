package games.dripdrop.simustock.presenter.utils

import java.text.SimpleDateFormat
import java.util.*

object TextFormatManager {

    fun createStockCode(): String {
        return StringBuilder("DD")
            .append(SimpleDateFormat("yyMMddHHmm", Locale.PRC).format(Date()))
            .append(UUID.randomUUID().toString().split(Regex("-")).first().uppercase())
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

    fun createDateTime(
        format: String = "yyyy-MM-dd HH:mm:ss",
        locale: Locale = Locale.PRC,
        date: Date = Date()
    ): String = SimpleDateFormat(format, locale).format(date)

    // 过长文本进行截断，目前每15个字符为一组
    fun formatLongText(content: String, limit: Int = 15): String {
        val repeat = content.length / limit
        val rest = content.length % limit
        return if (0 >= repeat) {
            content
        } else {
            StringBuilder().apply {
                var start = 0
                repeat(repeat) {
                    append(content.substring(start, start + limit)).append("\n")
                    start += limit
                }
                append(content.substring(content.lastIndex - rest, content.lastIndex))
            }.toString()
        }
    }
}