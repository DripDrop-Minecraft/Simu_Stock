package games.dripdrop.simustock.center

import games.dripdrop.simustock.bean.Company
import games.dripdrop.simustock.bean.Localization
import games.dripdrop.simustock.bean.PluginFile
import games.dripdrop.simustock.bean.SystemConfig
import games.dripdrop.simustock.database.SQLiteDatabaseManager
import games.dripdrop.simustock.interfaces.IExchange
import games.dripdrop.simustock.interfaces.ISecuritiesDealer
import games.dripdrop.simustock.utils.JsonManager
import games.dripdrop.simustock.utils.PluginLogManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Color
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object SystemService {
    private val mSQLiteDatabaseManager = SQLiteDatabaseManager()
    private lateinit var mExchange: IExchange
    private lateinit var mPlugin: JavaPlugin
    private lateinit var mSecuritiesDealer: ISecuritiesDealer
    private var mEconomy: Economy? = null
    private var mPermission: Permission? = null

    fun runSimulatedStockMarket(plugin: JavaPlugin) {
        initPluginConfig(plugin.dataFolder)
        initRuntimeEnvironment(plugin)
        initDatabase()
        runStockSystem()
    }

    fun stopSimulatedStockMarket() {
        mSQLiteDatabaseManager.deinitDatabase()
        mEconomy = null
        mPermission = null
    }

    fun getConfig(): SystemConfig {
        return JsonManager.getSingleObject(
            "${mPlugin.dataFolder.path}${File.separatorChar}${PluginFile.CONFIG_FILE.fileName}",
            SystemConfig()
        )
    }

    fun getLocalization(): Localization {
        return JsonManager.getSingleObject(
            "${mPlugin.dataFolder.path}${File.separatorChar}${PluginFile.LOCALIZATION_FILE.fileName}",
            Localization()
        )
    }

    fun getCompanies(): List<Company?> {
        return JsonManager.getObjectList(
            "${mPlugin.dataFolder.path}${File.separatorChar}${PluginFile.COMPANY_LIST_FILE.fileName}"
        )
    }

    fun hasPermission(player: Player, permission: String): Boolean {
        return (true == mPermission?.has(player, permission) || player.hasPermission(permission)).apply {
            if (!this) {
                player.sendMessage("${Color.GREEN}[SimuStock] ${Color.RED}${getLocalization().noPermission}")
            }
        }
    }

    @Throws(RuntimeException::class)
    private fun initRuntimeEnvironment(plugin: JavaPlugin) {
        PluginLogManager.i("init runtime: check permission and plugin Vault...")
        mPlugin = plugin
        if (plugin.server.pluginManager.getPlugin("Vault") == null) {
            throw RuntimeException("I cannot run without plugin Vault!")
        }
        mPermission = plugin.server.servicesManager.getRegistration(Permission::class.java)?.provider
        if (mPermission == null) {
            PluginLogManager.w("No permission plugin supporting Vault found, use original apis")
        }
        mEconomy = plugin.server.servicesManager.getRegistration(Economy::class.java)?.provider
        if (mEconomy == null) {
            throw RuntimeException("I cannot run without provided economy service!")
        } else {
            PluginLogManager.i("init IExchange and ISecuritiesDealer objects...")
            mExchange = SystemExchange(mSQLiteDatabaseManager)
            mSecuritiesDealer = SystemSecuritiesDealer(mSQLiteDatabaseManager, mEconomy!!)
        }
    }

    private fun initPluginConfig(file: File) {
        PluginLogManager.i("init plugin config now...")
        JsonManager.checkFileOrDirectoryAvailable(file, PluginFile.LOCALIZATION_FILE)
        JsonManager.checkFileOrDirectoryAvailable(file, PluginFile.CONFIG_FILE)
        JsonManager.checkFileOrDirectoryAvailable(file, PluginFile.COMPANY_LIST_FILE)
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