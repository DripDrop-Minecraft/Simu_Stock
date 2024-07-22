package games.dripdrop.simustock.presenter.database

import com.zaxxer.hikari.HikariConfig
import games.dripdrop.simustock.model.bean.*
import games.dripdrop.simustock.presenter.SystemService
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import games.dripdrop.simustock.presenter.utils.TextFormatManager
import java.io.File

class SQLiteDatabaseManager : AbstractDatabaseManager() {
    private val mCompanies = mutableListOf<Company>()
    private val mOrders = mutableListOf<Order>()
    private val mAssets = mutableListOf<Asset>()
    private val mAnnouncements = mutableListOf<Announcement>()

    override fun createTables() {
        getDataSource()?.connection?.use { it.update(createCompanyTable(), mapOf()) {} }
        getDataSource()?.connection?.use { it.update(createOrderTable(), mapOf()) {} }
        getDataSource()?.connection?.use { it.update(createAssetTable(), mapOf()) {} }
        getDataSource()?.connection?.use { it.update(createAnnouncementTable(), mapOf()) {} }
    }

    fun createConnectionConfig(path: String, name: String, pwd: String): HikariConfig {
        checkDatabaseFile(path)
        return HikariConfig().apply {
            jdbcUrl = "jdbc:sqlite:$path"
            username = name
            password = pwd
            connectionTimeout = 10 * 1000L
            maximumPoolSize = 32
        }
    }

    fun insertCompanies(list: List<Company?>) {
        getDataSource()?.connection?.batch(createInsertAllDataSQL<Company>()) { ps ->
            list.onEach {
                if (it != null) {
                    listOf(
                        TextFormatManager.createStockCode(), it.name, it.desc, it.currentStockNum, it.currentPrice,
                        it.isListed, it.riskLevel, it.availableFunds, it.debt, it.listedTime, it.delistedTime
                    ).apply { ps.setAllPropsOnce<Company>(this) }
                    ps.addBatch()
                }
            }
        }
    }

    fun deleteCompany(vararg stockCodes: String) {
        val sql = createDeleteWithConditionSQL<Company>(stockCodes.isNotEmpty())
            .apply {
                if (stockCodes.isNotEmpty()) {
                    append("stockCode = ?")
                }
            }
            .toString()
        getDataSource()?.connection?.apply {
            if (stockCodes.isNotEmpty()) {
                batch(sql) { ps ->
                    stockCodes.onEach {
                        ps.setString(1, it)
                        ps.addBatch()
                    }
                }
            } else {
                update(sql, emptyMap()) { PluginLogManager.i("update result: $it") }
            }
        }
    }

    fun queryCompanyByStockCode(vararg stockCodes: String, callback: (List<Company>) -> Unit) {
        val sql = createQueryWithConditionSQL<Company>(stockCodes.isNotEmpty())
            .apply {
                if (stockCodes.isNotEmpty()) {
                    append("(")
                    repeat(stockCodes.size) { append("stockCode = ? OR ") }
                    append(")")
                }
            }.toString().replace(" OR )", ")")
        val sqlMap = linkedMapOf<Int, String>().apply {
            stockCodes.onEachIndexed { index, stockCode -> put(index + 1, stockCode) }
        }
        getDataSource()?.connection?.query(sql, sqlMap) {
            mCompanies.clear()
            while (it.next()) {
                mCompanies.add(
                    Company(
                        it.getString(1), it.getString(2), it.getString(3),
                        it.getInt(4), it.getDouble(5), it.getBoolean(6),
                        it.getInt(7), it.getDouble(8), it.getDouble(9),
                        it.getLong(10), it.getLong(11)
                    )
                )
            }
            callback(mCompanies)
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
                    it.dealingPrice, it.isBuying, it.state
                ).apply { ps.setAllPropsOnce<Order>(this) }
                ps.addBatch()
            }
        }
    }

    fun queryOrdersByOrderNumber(vararg orderNumbers: String, callback: (List<Order>) -> Unit) {
        val sql = createQueryWithConditionSQL<Order>(orderNumbers.isNotEmpty())
            .apply {
                if (orderNumbers.isNotEmpty()) {
                    append("(")
                    repeat(orderNumbers.size) { append("orderNumber = ? OR ") }
                    append(")")
                }
            }.toString().replace(" OR )", ")")
        val sqlMap = linkedMapOf<Int, String>().apply {
            orderNumbers.onEachIndexed { index, on -> put(index + 1, on) }
        }
        getDataSource()?.connection?.query(sql, sqlMap) {
            mOrders.clear()
            while (it.next()) {
                mOrders.add(
                    Order(
                        it.getString(1), it.getLong(2),
                        it.getString(3), it.getString(4),
                        it.getString(5), it.getString(6),
                        it.getInt(7), it.getDouble(8),
                        it.getBoolean(9), it.getInt(10)
                    )
                )
            }
            callback(mOrders)
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

    fun queryAssetsByInvestorUUIDAndStockCode(
        investorUUID: String,
        vararg stockCodes: String,
        callback: (List<Asset>) -> Unit
    ) {
        val sql = createQueryWithConditionSQL<Asset>(stockCodes.isNotEmpty())
            .apply {
                if (stockCodes.isNotEmpty()) {
                    append("(investorUUID = ? AND (")
                    repeat(stockCodes.size) { append("stockCode = ? OR ") }
                    append("))")
                }
            }.toString().replace(" OR )", ")")
        val sqlMap = linkedMapOf(1 to investorUUID).apply {
            stockCodes.onEachIndexed { index, stockCode -> put(index + 2, stockCode) }
        }
        getDataSource()?.connection?.query(sql, sqlMap) {
            mAssets.clear()
            while (it.next()) {
                mAssets.add(
                    Asset(
                        it.getString(1), it.getString(2),
                        it.getString(3), it.getString(4),
                        it.getInt(5), it.getDouble(6),
                        it.getDouble(7), it.getDouble(8),
                        it.getDouble(9)
                    )
                )
            }
            callback(mAssets)
        }
    }

    fun updateAssetInfoByInvestorUUIDAndStockCode(investorUUID: String, stockCode: String, map: Map<String, Any>) {
        val sql = createUpdateWithConditionSQL<Asset>(map)
            .append("investorUUID = ? AND stockCode = ?")
            .toString()
        val sqlMap = linkedMapOf<Int, Any>().apply {
            map.onEachIndexed { index, entry ->
                put(index + 1, entry.value)
            }
            put(map.size + 1, investorUUID)
            put(map.size + 2, stockCode)
        }
        getDataSource()?.connection?.update(sql, sqlMap) { PluginLogManager.i("update result: $it") }
    }

    fun insertAnnouncement(vararg announcements: Announcement) {
        getDataSource()?.connection?.batch(createInsertAllDataSQL<Announcement>()) { ps ->
            announcements.onEach {
                listOf(it.timestamp, it.content).apply { ps.setAllPropsOnce<Asset>(this) }
                ps.addBatch()
            }
        }
    }

    fun queryAllAnnouncements(callback: (List<Announcement>) -> Unit) {
        val sql = createQueryWithConditionSQL<Announcement>(false)
            .append(" ORDER BY timestamp DESC")
            .toString()
        getDataSource()?.connection?.query(sql, mapOf()) {
            mAnnouncements.clear()
            while (it.next()) {
                mAnnouncements.add(
                    Announcement(
                        it.getLong(1),
                        it.getString(2),
                        it.getString(3)
                    )
                )
            }
            callback(mAnnouncements)
        }
    }

    private fun checkDatabaseFile(path: String) {
        val dir = File(SystemService.getRootPath())
        val file = File(path)
        val isPathAvailable = file.exists()
        if (!dir.exists()) {
            dir.mkdir()
        }
        if (!isPathAvailable) {
            file.createNewFile()
        }
        PluginLogManager.i("is file available: $isPathAvailable")
    }

    private fun createCompanyTable() = createTableCreatingSQL(
        TextFormatManager.createTableName<Company>(),
        ColumnProp("stockCode", "TEXT", "PRIMARY KEY"),
        ColumnProp("name", "TEXT", "NOT NULL"),
        ColumnProp("desc", "TEXT", "NOT NULL"),
        ColumnProp("currentStockNum", "INTEGER", "CHECK(currentStockNum >= 0)"),
        ColumnProp("currentPrice", "DOUBLE", "CHECK(currentPrice >= 0.0)"),
        ColumnProp("isListed", "BOOLEAN", "NOT NULL"),
        ColumnProp("riskLevel", "INTEGER", "NOT NULL"),
        ColumnProp("availableFunds", "DOUBLE", "CHECK(availableFunds >= 0.0)"),
        ColumnProp("debt", "DOUBLE", "CHECK(debt >= 0.0)"),
        ColumnProp("listedTime", "LONG", "NOT NULL"),
        ColumnProp("delistedTime", "LONG", "NOT NULL"),
    )

    private fun createOrderTable() = createTableCreatingSQL(
        TextFormatManager.createTableName<Order>(),
        ColumnProp("orderNumber", "TEXT", "PRIMARY KEY"),
        ColumnProp("timestamp", "LONG", "NOT NULL"),
        ColumnProp("stockName", "TEXT", "NOT NULL"),
        ColumnProp("stockCode", "TEXT", "NOT NULL"),
        ColumnProp("investorName", "TEXT", "NOT NULL"),
        ColumnProp("investorUUID", "TEXT", "NOT NULL"),
        ColumnProp("dealingAmount", "INTEGER", "CHECK(dealingAmount > 0)"),
        ColumnProp("dealingPrice", "DOUBLE", "CHECK(dealingPrice > 0.0)"),
        ColumnProp("isBuying", "BOOLEAN", "NOT NULL"),
        ColumnProp("state", "INTEGER", "NOT NULL")
    )

    private fun createAssetTable() = createTableCreatingSQL(
        TextFormatManager.createTableName<Asset>(),
        ColumnProp("stockCode", "TEXT", "NOT NULL"),
        ColumnProp("investorUUID", "TEXT", "NOT NULL"),
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

    private fun createAnnouncementTable() = createTableCreatingSQL(
        TextFormatManager.createTableName<Announcement>(),
        ColumnProp("timestamp", "LONG", "PRIMARY KEY"),
        ColumnProp("title", "TEXT", "NOT NULL"),
        ColumnProp("content", "TEXT", "NOT NULL")
    )
}