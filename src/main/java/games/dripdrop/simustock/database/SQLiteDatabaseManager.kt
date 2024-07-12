package games.dripdrop.simustock.database

import com.zaxxer.hikari.HikariConfig
import games.dripdrop.simustock.bean.*
import games.dripdrop.simustock.center.SystemService
import games.dripdrop.simustock.utils.PluginLogManager
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
                    listOf(
                        it.stockCode, it.name, it.desc, it.currentStockNum,
                        it.currentPrice, it.isListed, it.riskLevel
                    ).apply { ps.setAllPropsOnce<Company>(this) }
                    ps.addBatch()
                }
            }
        }
    }

    fun updateCompanyInfo(stockCode: String, map: Map<String, Any>) {
        val sql = createUpdateWithConditionSQL<Company>(map)
            .append("stockCode = ?")
            .toString()
        val sqlMap = linkedMapOf<Int, Any>().apply {
            map.onEachIndexed { index, entry ->
                put(index + 1, entry.value)
            }
            put(map.size + 1, stockCode)
        }
        getDataSource()?.connection?.update(sql, sqlMap) { PluginLogManager.i("update result: $it") }
    }

    fun insertOrders(vararg orders: Order) {
        getDataSource()?.connection?.batch(createInsertAllDataSQL<Order>()) { ps ->
            orders.onEach {
                listOf(
                    it.orderNumber, it.timestamp, it.stockName, it.stockCode,
                    it.investorName, it.investorUUID, it.dealingAmount,
                    it.dealingPrice, it.isBuying
                ).apply { ps.setAllPropsOnce<Order>(this) }
                ps.addBatch()
            }
        }
    }

    fun insertAssets(vararg assets: Asset) {
        getDataSource()?.connection?.batch(createInsertAllDataSQL<Asset>()) { ps ->
            assets.onEach {
                listOf(
                    it.stockCode, it.investorUUID, it.stockName, it.investorName,
                    it.holdingNum, it.holdingCost, it.totalHoldingMarketCap,
                    it.currentDiff, it.diffRate
                ).apply { ps.setAllPropsOnce<Asset>(this) }
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
}