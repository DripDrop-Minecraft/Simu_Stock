package games.dripdrop.simustock.presenter

import games.dripdrop.simustock.model.bean.Company
import games.dripdrop.simustock.model.constants.Localization
import games.dripdrop.simustock.model.constants.PluginFile
import games.dripdrop.simustock.model.constants.SystemConfig
import games.dripdrop.simustock.presenter.database.SQLiteDatabaseManager
import games.dripdrop.simustock.presenter.engine.SystemExchange
import games.dripdrop.simustock.presenter.engine.SystemSecuritiesDealer
import games.dripdrop.simustock.presenter.interfaces.IExchange
import games.dripdrop.simustock.presenter.interfaces.ISecuritiesDealer
import games.dripdrop.simustock.presenter.utils.JsonManager
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import kotlinx.coroutines.*
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Color
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object SystemService {
    private val mSQLiteDatabaseManager = SQLiteDatabaseManager()
    private lateinit var mPlugin: JavaPlugin
    private lateinit var mExchange: IExchange
    private lateinit var mSecuritiesDealer: ISecuritiesDealer
    private var mEconomy: Economy? = null
    private var mPermission: Permission? = null
    private var mIsInit: Boolean = false
    private var mRootPath = ""

    fun runSimulatedStockMarket(plugin: JavaPlugin) {
        if (mIsInit) {
            PluginLogManager.w("already init")
            return
        }
        initDataPath(plugin)
        initPluginConfig()
        initRuntimeEnvironment()
        initDatabase()
        runStockSystem()
    }

    fun stopSimulatedStockMarket() {
        mSQLiteDatabaseManager.deinitDatabase()
        mEconomy = null
        mPermission = null
    }

    fun getRootPath(): String = mRootPath

    @Throws(IllegalStateException::class)
    fun getPlugin(): JavaPlugin {
        if (!mIsInit) {
            throw IllegalStateException("system service has not been initialized yet!")
        }
        return mPlugin
    }

    @Throws(IllegalStateException::class)
    fun getSQLiteManager(): SQLiteDatabaseManager {
        if (!mIsInit) {
            throw IllegalStateException("system service has not been initialized yet!")
        }
        return mSQLiteDatabaseManager
    }

    @Throws(IllegalStateException::class)
    fun getExchange(): IExchange {
        if (!mIsInit) {
            throw IllegalStateException("system service has not been initialized yet!")
        }
        return mExchange
    }

    @Throws(IllegalStateException::class)
    fun getSecuritiesDealer(): ISecuritiesDealer {
        if (!mIsInit) {
            throw IllegalStateException("system service has not been initialized yet!")
        }
        return mSecuritiesDealer
    }

    fun getConfig(): SystemConfig {
        return JsonManager.getSingleObject(
            "${getRootPath()}${PluginFile.CONFIG_FILE.fileName}",
            PluginFile.CONFIG_FILE,
            SystemConfig()
        )
    }

    fun getLocalization(): Localization {
        return JsonManager.getSingleObject(
            "${getRootPath()}${PluginFile.LOCALIZATION_FILE.fileName}",
            PluginFile.LOCALIZATION_FILE,
            Localization()
        )
    }

    fun getCompanies(): List<Company?> {
        return JsonManager.getObjectList(
            "${getRootPath()}${PluginFile.COMPANY_LIST_FILE.fileName}",
            PluginFile.COMPANY_LIST_FILE
        )
    }

    fun hasPermission(player: Player, permission: String): Boolean {
        return (true == mPermission?.has(player, permission) || player.hasPermission(permission)).apply {
            if (!this) {
                player.sendMessage("${Color.GREEN}[SimuStock] ${Color.RED}${getLocalization().noPermission}")
            }
        }
    }

    private fun initDataPath(javaPlugin: JavaPlugin) {
        PluginLogManager.i("init data path")
        mPlugin = javaPlugin
        mRootPath = "${javaPlugin.dataFolder}${File.separatorChar}"
        File(mRootPath).apply {
            if (!isDirectory && !exists()) {
                mkdir()
            }
            PluginLogManager.i("is data path available: ${exists()}")
        }
    }

    @Throws(RuntimeException::class)
    private fun initRuntimeEnvironment() {
        PluginLogManager.i("init runtime: check permission and plugin Vault...")
        if (mPlugin.server.pluginManager.getPlugin("Vault") == null) {
            throw RuntimeException("I cannot run without plugin Vault!")
        }
        mPermission = mPlugin.server.servicesManager.getRegistration(Permission::class.java)?.provider
        if (mPermission == null) {
            PluginLogManager.w("No permission plugin supporting Vault found, use original apis")
        }
        mEconomy = mPlugin.server.servicesManager.getRegistration(Economy::class.java)?.provider
        if (mEconomy == null) {
            throw RuntimeException("I cannot run without provided economy service!")
        } else {
            PluginLogManager.i("init IExchange and ISecuritiesDealer objects...")
            mExchange = SystemExchange(mSQLiteDatabaseManager)
            mSecuritiesDealer = SystemSecuritiesDealer(mEconomy!!)
        }
        mIsInit = true
    }

    private fun initPluginConfig() {
        PluginLogManager.i("init plugin config now...")
        JsonManager.checkFileOrDirectoryAvailable(mPlugin.dataFolder, PluginFile.LOCALIZATION_FILE)
        JsonManager.checkFileOrDirectoryAvailable(mPlugin.dataFolder, PluginFile.CONFIG_FILE)
    }

    private fun initDatabase() {
        PluginLogManager.i("init database now...")
        mSQLiteDatabaseManager.apply {
            initDatabase(
                createConnectionConfig(
                    "${getRootPath()}${PluginFile.DATABASE_FILE.fileName}",
                    getConfig().databaseAccount, getConfig().databasePassword
                )
            )
        }
    }

    private fun runStockSystem() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                PluginLogManager.i("recycle")
                // TODO
                delay(15 * 1000L)
            }
        }
    }
}