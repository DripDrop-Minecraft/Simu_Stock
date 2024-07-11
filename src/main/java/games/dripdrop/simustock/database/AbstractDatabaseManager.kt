package games.dripdrop.simustock.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.util.IsolationLevel
import games.dripdrop.simustock.interfaces.IDatabase
import games.dripdrop.simustock.utils.PluginLogManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

abstract class AbstractDatabaseManager : IDatabase {
    private var mDataSource: HikariDataSource? = null

    override fun initDatabase(config: HikariConfig) {
        PluginLogManager.i("start to init database")
        if (mDataSource != null && true == mDataSource?.isRunning) {
            PluginLogManager.i("database has been initialized")
            return
        }
        try {
            mDataSource = HikariDataSource(config)
            createTables()
        } catch (e: Exception) {
            PluginLogManager.e("failed to init database connection: ${e.localizedMessage}")
            e.printStackTrace()
        }
    }

    override fun deinitDatabase() {
        PluginLogManager.i("deinit database")
        mDataSource?.close()
        mDataSource = null
    }

    override fun getDataSource(): HikariDataSource? = mDataSource.apply {
        PluginLogManager.i("is data source null: ${this == null}")
    }

    override fun Connection.query(
        sql: String,
        map: Map<Int, Any>,
        callback: (ResultSet) -> Unit
    ) {
        use {
            try {
                prepareStatement(sql).apply {
                    map.forEach { (k, v) -> setObject(k, v) }
                }.use { it.executeQuery().use(callback) }
            } catch (e: Exception) {
                PluginLogManager.e("failed to query: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    override fun Connection.update(sql: String, map: Map<Int, Any>, callback: (Int) -> Unit) {
        use {
            try {
                prepareStatement(sql).apply {
                    map.forEach { (k, v) -> setObject(k, v) }
                }.use { callback(it.executeUpdate()) }
            } catch (e: Exception) {
                PluginLogManager.e("failed to update: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    override fun Connection.batch(sql: String, callback: (PreparedStatement) -> Unit) {
        use {
            try {
                prepareStatement(sql).use {
                    callback(it)
                    it.executeBatch()
                }
            } catch (e: Exception) {
                PluginLogManager.e("failed to batch: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    override fun Connection.issueTransaction(isolationLevel: IsolationLevel, transaction: () -> Unit) {
        use {
            try {
                transactionIsolation = isolationLevel.levelId
                autoCommit = false
                transaction()
                commit()
            } catch (e: Exception) {
                PluginLogManager.e("failed to issue transaction: ${e.localizedMessage}")
                e.printStackTrace()
                if (e is SQLException) {
                    rollback()
                }
            } finally {
                autoCommit = true
            }
        }
    }

    abstract fun createTables()
}