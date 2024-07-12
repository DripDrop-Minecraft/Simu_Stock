package games.dripdrop.simustock.center

import games.dripdrop.simustock.bean.Company
import games.dripdrop.simustock.bean.PluginFile
import games.dripdrop.simustock.bean.SystemConfig
import games.dripdrop.simustock.database.SQLiteDatabaseManager
import games.dripdrop.simustock.interfaces.IExchange
import games.dripdrop.simustock.interfaces.ISecuritiesDealer
import games.dripdrop.simustock.utils.JsonManager
import games.dripdrop.simustock.utils.PluginLogManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object SystemService {
    private val mExchange: IExchange = SystemExchange()
    private val mSecuritiesDealer: ISecuritiesDealer = SystemSecuritiesDealer()
    private val mSQLiteDatabaseManager = SQLiteDatabaseManager()
    private lateinit var mPlugin: JavaPlugin

    fun runSimulatedStockMarket(plugin: JavaPlugin) {
        initPluginConfig(plugin)
        initDatabase()
        runStockSystem()
    }

    fun stopSimulatedStockMarket() {
        mSQLiteDatabaseManager.deinitDatabase()
    }

    fun getConfig(): SystemConfig {
        return JsonManager.getSingleObject(
            "${mPlugin.dataFolder.path}${File.separatorChar}${PluginFile.CONFIG_FILE.fileName}",
            SystemConfig()
        )
    }

    fun getCompanies(): List<Company?> {
        return JsonManager.getObjectList(
            "${mPlugin.dataFolder.path}${File.separatorChar}${PluginFile.COMPANY_LIST_FILE.fileName}"
        )
    }

    private fun initPluginConfig(plugin: JavaPlugin) {
        PluginLogManager.i("init plugin config now...")
        mPlugin = plugin
        JsonManager.checkFileOrDirectoryAvailable(plugin.dataFolder, PluginFile.CONFIG_FILE)
        JsonManager.checkFileOrDirectoryAvailable(plugin.dataFolder, PluginFile.COMPANY_LIST_FILE)
    }

    private fun initDatabase() {
        PluginLogManager.i("init database now...")
        mSQLiteDatabaseManager.apply {
            initDatabase(createConnectionConfig(getConfig().databaseAccount, getConfig().databasePassword))
        }
    }

    private fun runStockSystem() {
        runBlocking {
            while (true) {
                // TODO
                delay(15 * 1000L)
            }
        }
    }
}