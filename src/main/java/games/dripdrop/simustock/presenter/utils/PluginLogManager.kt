package games.dripdrop.simustock.presenter.utils

import games.dripdrop.simustock.SimuStockPlugin
import org.slf4j.LoggerFactory

object PluginLogManager {
    private val mLogger = LoggerFactory.getLogger(SimuStockPlugin::class.java.simpleName)

    fun d(msg: String) = mLogger.debug(msg)

    fun i(msg: String) = mLogger.info(msg)

    fun w(msg: String) = mLogger.warn(msg)

    fun e(msg: String) = mLogger.error(msg)
}