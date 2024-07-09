package games.dripdrop.simustock.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

object JsonManager {
    val mGson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .setLenient()
        .serializeNulls()
        .disableHtmlEscaping()
        .create()

    inline fun <reified T> readJsonFromFile(path: String, defaultValue: T? = null): T? {
        return try {
            PluginLogManager.i("type = ${T::class.java.simpleName}, file path = [$path]")
            BufferedReader(InputStreamReader(FileInputStream(path), Charset.defaultCharset().name())).use {
                val sb = StringBuilder()
                var s = it.readLine()
                while (s != null) {
                    sb.append(s)
                    s = it.readLine()
                }
                return if (sb.toString().isNotEmpty()) {
                    mGson.fromJson(sb.toString(), T::class.java)
                } else {
                    defaultValue
                }
            }
        } catch (e: Exception) {
            PluginLogManager.e("trying to read Json from file [$path] failed: ${e.localizedMessage}")
            e.printStackTrace()
            defaultValue
        }
    }
}