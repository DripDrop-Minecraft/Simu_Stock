package games.dripdrop.simustock.database

import com.zaxxer.hikari.HikariConfig
import games.dripdrop.simustock.bean.*
import games.dripdrop.simustock.center.SystemService
import games.dripdrop.simustock.utils.UniqueIDManager

class SQLiteDatabaseManager : AbstractDatabaseManager() {

    override fun createTables() {
        getDataSource()?.connection?.use { it.update(createCompanyTable(), mapOf()) {} }
        getDataSource()?.connection?.use { it.update(createOrderTable(), mapOf()) {} }
        getDataSource()?.connection?.use { it.update(createAssetTable(), mapOf()) {} }
    }

    fun createConnectionConfig(name: String, pwd: String): HikariConfig {
        return HikariConfig().apply {
            jdbcUrl = "jdbc:sqlite:${PluginFile.DATABASE_FILE.fileName}"
            username = name
            password = pwd
            connectionTimeout = 10 * 1000L
            maximumPoolSize = 32
        }
    }

    fun importCompaniesFromFile() {
        getDataSource()?.connection?.batch(createInsertAllDataSQL<Company>()) { ps ->
            SystemService.getCompanies().onEach {
                if (it != null) {
                    ps.setString(1, it.stockCode)
                    ps.setString(2, it.name)
                    ps.setString(3, it.desc)
                    ps.setInt(4, it.currentStockNum)
                    ps.setDouble(5, it.currentPrice)
                    ps.setBoolean(6, it.isListed)
                    ps.setInt(7, it.riskLevel)
                    ps.addBatch()
                }
            }
        }
    }

    fun insertOrders(vararg orders: Order) {
        getDataSource()?.connection?.batch(createInsertAllDataSQL<Order>()) { ps ->
            orders.onEach {
                ps.setString(1, it.orderNumber)
                ps.setLong(2, it.timestamp)
                ps.setString(3, it.stockName)
                ps.setString(4, it.stockCode)
                ps.setString(5, it.orderNumber)
                ps.setString(6, it.investorName)
                ps.setString(7, it.investorUUID)
                ps.setInt(8, it.dealingAmount)
                ps.setDouble(9, it.dealingPrice)
                ps.setBoolean(10, it.isBuying)
                ps.addBatch()
            }
        }
    }

    fun insertAssets(vararg assets: Asset) {
        getDataSource()?.connection?.batch(createInsertAllDataSQL<Asset>()) { ps ->
            assets.onEach {
                ps.setString(1, it.stockCode)
                ps.setString(2, it.investorUUID)
                ps.setString(3, it.stockName)
                ps.setString(4, it.investorName)
                ps.setInt(5, it.holdingNum)
                ps.setDouble(6, it.holdingCost)
                ps.setDouble(7, it.totalHoldingMarketCap)
                ps.setDouble(8, it.currentDiff)
                ps.setDouble(9, it.diffRate)
                ps.addBatch()
            }
        }
    }

    private fun createCompanyTable() = createTableCreatingSQL(
        UniqueIDManager.createTableName<Company>(),
        ColumnProp("stockCode", "TEXT", "PRIMARY KEY"),
        ColumnProp("name", "TEXT", "NOT NULL"),
        ColumnProp("desc", "TEXT", "NOT NULL"),
        ColumnProp("currentStockNum", "INTEGER", "CHECK(currentStockNum >= 0)"),
        ColumnProp("currentPrice", "DOUBLE", "CHECK(currentPrice >= 0.0)"),
        ColumnProp("isListed", "BOOLEAN", "NOT NULL"),
        ColumnProp("riskLevel", "INTEGER", "NOT NULL")
    )

    private fun createOrderTable() = createTableCreatingSQL(
        UniqueIDManager.createTableName<Order>(),
        ColumnProp("orderNumber", "TEXT", "PRIMARY KEY"),
        ColumnProp("timestamp", "LONG", "NOT NULL"),
        ColumnProp("stockName", "TEXT", "NOT NULL"),
        ColumnProp("stockCode", "TEXT", "NOT NULL"),
        ColumnProp("investorName", "TEXT", "NOT NULL"),
        ColumnProp("investorUUID", "TEXT", "NOT NULL"),
        ColumnProp("dealingAmount", "INTEGER", "CHECK(dealingAmount > 0)"),
        ColumnProp("dealingPrice", "DOUBLE", "CHECK(dealingPrice > 0.0)"),
        ColumnProp("isBuying", "BOOLEAN", "NOT NULL")
    )

    private fun createAssetTable() = createTableCreatingSQL(
        UniqueIDManager.createTableName<Asset>(),
        ColumnProp("stockCode", "TEXT", "NOT NULL UNIQUE"),
        ColumnProp("investorUUID", "TEXT", "NOT NULL UNIQUE"),
        ColumnProp("stockName", "TEXT", "NOT NULL"),
        ColumnProp("investorName", "TEXT", "NOT NULL"),
        ColumnProp("holdingNum", "INTEGER", "CHECK(holdingNum >= 0)"),
        ColumnProp("holdingCost", "DOUBLE", "CHECK(holdingCost > 0.0)"),
        ColumnProp("totalHoldingMarketCap", "DOUBLE", "CHECK(totalHoldingMarketCap > 0.0)"),
        ColumnProp("currentDiff", "DOUBLE", "NOT NULL"),
        ColumnProp("diffRate", "DOUBLE", "NOT NULL")
    ).replace(
        "NULL)",
        "NULL, PRIMARY KEY(stockCode, investorUUID))"
    )

    inline fun <reified T> createInsertAllDataSQL(): String {
        return StringBuilder("INSERT INTO ")
            .append(UniqueIDManager.createTableName<T>())
            .append(" VALUES (")
            .apply {
                repeat(T::class.java.declaredFields.size) {
                    append("?,")
                }
            }.append(")")
            .toString().replace(",)", ")")
    }
}