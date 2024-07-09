package games.dripdrop.simustock.utils

import java.util.logging.Level
import java.util.logging.Logger

object PluginLogManager {
    private const val TAG = "SimuStock"
    private val mLogger = Logger.getLogger(TAG)

    fun v(msg: String) = log(Level.ALL, msg)

    fun i(msg: String) = log(Level.INFO, msg)

    fun c(msg: String) = log(Level.CONFIG, msg)

    fun w(msg: String) = log(Level.WARNING, msg)

    fun e(msg: String) = log(Level.SEVERE, msg)

    private fun log(level: Level, msg: String) {
        mLogger.log(level, "$TAG  $msg")
    }
}