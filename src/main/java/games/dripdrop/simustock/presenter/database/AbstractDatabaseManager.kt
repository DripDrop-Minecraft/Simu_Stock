package games.dripdrop.simustock.presenter.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.util.IsolationLevel
import games.dripdrop.simustock.model.bean.ColumnProp
import games.dripdrop.simustock.presenter.interfaces.IDatabase
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import games.dripdrop.simustock.presenter.utils.TextFormatManager
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
        if (this == null) {
            PluginLogManager.i("data source is null")
        }
    }

    override fun Connection.query(
        sql: String,
        map: Map<Int, Any>,
        callback: (ResultSet) -> Unit
    ) {
        PluginLogManager.d("sql of query: $sql")
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
        PluginLogManager.d("sql of update: $sql")
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
        PluginLogManager.d("sql of batch: $sql")
        use {
            try {
                prepareStatement(sql).use {
                    callback(it)
                    it.executeBatch().apply { PluginLogManager.d("size of batch results = $size") }
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

    protected inline fun <reified T> createDeleteWithConditionSQL(hasConditions: Boolean): StringBuilder {
        return StringBuilder("DELETE FROM ")
            .append(TextFormatManager.createTableName<T>())
            .apply {
                if (hasConditions) {
                    append(" WHERE ")
                }
            }
    }

    protected inline fun <reified T> createQueryWithConditionSQL(hasConditions: Boolean): StringBuilder {
        return StringBuilder("SELECT * FROM ")
            .append(TextFormatManager.createTableName<T>())
            .apply {
                if (hasConditions) {
                    append(" WHERE ")
                }
            }
    }

    protected inline fun <reified T> createUpdateWithConditionSQL(map: Map<String, Any>): StringBuilder {
        return StringBuilder(StringBuilder("UPDATE ")
            .append(TextFormatManager.createTableName<T>())
            .append(" SET ")
            .apply {
                map.keys.onEach { append("$it = ?, ") }
            }.append("WHERE ").replace(Regex(", WHERE"), " WHERE")
        )
    }

    protected inline fun <reified T> createInsertAllDataSQL(): String {
        return StringBuilder("INSERT INTO ")
            .append(TextFormatManager.createTableName<T>())
            .append(" VALUES (")
            .apply {
                repeat(T::class.java.declaredFields.size) {
                    append("?,")
                }
            }.append(")")
            .toString().replace(",)", ")")
    }

    protected inline fun <reified T> PreparedStatement.setAllPropsOnce(list: List<Any>) {
        if (T::class.java.declaredFields.size != list.size) {
            throw IllegalArgumentException("List and [${T::class.java.simpleName}]'s declared fields are not matched")
        }
        T::class.java.declaredFields.forEachIndexed { index, field ->
            when (field.type.simpleName) {
                String::class.java.simpleName -> {
                    setString(index + 1, (list[index] as String))
                }

                Int::class.java.simpleName.lowercase() -> {
                    setInt(index + 1, (list[index] as Int))
                }

                Double::class.java.simpleName.lowercase() -> {
                    setDouble(index + 1, (list[index] as Double))
                }

                Long::class.java.simpleName.lowercase() -> {
                    setLong(index + 1, (list[index] as Long))
                }

                Boolean::class.java.simpleName.lowercase() -> {
                    setBoolean(index + 1, (list[index] as Boolean))
                }

                else -> throw IllegalArgumentException("Invalid value in list[$index]")
            }
        }
    }

    protected fun createTableCreatingSQL(tableName: String, vararg columnProps: ColumnProp): String {
        return StringBuilder("CREATE TABLE IF NOT EXISTS ")
            .append(tableName)
            .append(" (")
            .apply {
                columnProps.forEach {
                    append("${it.columnName} ${it.columnType}${(" ${it.columnConstraint}").ifEmpty { "" }}, ")
                }
            }
            .append(")")
            .toString()
            .replace(", )", ")")
    }
}