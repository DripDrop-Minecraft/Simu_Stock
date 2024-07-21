package games.dripdrop.simustock.presenter.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import games.dripdrop.simustock.model.constants.PluginFile
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files

object JsonManager {
    val mGson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .setLenient()
        .serializeNulls()
        .disableHtmlEscaping()
        .create()


    inline fun <reified T> getObjectList(path: String, flag: PluginFile): List<T?> {
        PluginLogManager.i("type = ${T::class.java.simpleName}, file path = [$path]")
        if (!checkFileOrDirectoryAvailable(File(path.replace(flag.fileName, "")), flag)) {
            return emptyList()
        }
        val jsonString = readJsonFromFile(path)
        return if (jsonString.isNotEmpty()) {
            mGson.fromJson(jsonString, (object : TypeToken<List<T>>() {}.type))
        } else {
            emptyList()
        }
    }

    inline fun <reified T> getSingleObject(path: String, flag: PluginFile, defaultValue: T): T {
        PluginLogManager.i("type = ${T::class.java.simpleName}, file path = [$path]")
        if (!checkFileOrDirectoryAvailable(File(path.replace(flag.fileName, "")), flag)) {
            return defaultValue
        }
        val jsonString = readJsonFromFile(path)
        return if (jsonString.isNotEmpty()) {
            mGson.fromJson(jsonString, T::class.java)
        } else {
            defaultValue
        }
    }

    fun readJsonFromFile(path: String): String {
        PluginLogManager.i("try to read json string from file [$path]")
        return try {
            BufferedReader(InputStreamReader(FileInputStream(path), Charset.defaultCharset().name())).use {
                val sb = StringBuilder()
                var s = it.readLine()
                while (s != null) {
                    sb.append(s)
                    s = it.readLine()
                }
                return sb.toString()
            }
        } catch (e: Exception) {
            PluginLogManager.e("trying to read Json from file [$path] failed: ${e.localizedMessage}")
            e.printStackTrace()
            ""
        }
    }

    fun checkFileOrDirectoryAvailable(directory: File, file: PluginFile): Boolean {
        PluginLogManager.i("checkFileOrDirectoryAvailable called: ${directory.path}")
        return try {
            if (directory.isDirectory && !directory.exists()) {
                directory.mkdir()
            }
            PluginLogManager.i("directory [$directory] exists: ${directory.exists()}")
            val f = File(directory, file.fileName)
            PluginLogManager.i("file [$f] exists: ${f.exists()}")
            if (directory.exists() && !f.exists()) {
                javaClass.getResourceAsStream("/${file.fileName}")?.let {
                    val result = Files.copy(it, f.toPath())
                    PluginLogManager.i("file copy result: $result")
                }
            }
            f.exists()
        } catch (e: Exception) {
            PluginLogManager.e("failed to check or copy file: ${e.localizedMessage}")
            e.printStackTrace()
            false
        }
    }
}