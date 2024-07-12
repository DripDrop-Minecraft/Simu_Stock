package games.dripdrop.simustock.utils

import org.slf4j.LoggerFactory

object PluginLogManager {
    private val mLogger = LoggerFactory.getLogger(javaClass.simpleName)

    fun i(msg: String) = mLogger.info(msg)

    fun w(msg: String) = mLogger.warn(msg)

    fun e(msg: String) = mLogger.error(msg)
}