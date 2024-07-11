package games.dripdrop.simustock.database

import com.zaxxer.hikari.HikariConfig

object SQLiteDatabaseManager : AbstractDatabaseManager() {

    fun createConnectionConfig(name: String, pwd: String): HikariConfig {
        return HikariConfig().apply {
            jdbcUrl = "jdbc:sqlite:simustock.db"
            username = name
            password = pwd
            connectionTimeout = 10 * 1000L
            idleTimeout = 15 * 1000L
            maximumPoolSize = 32
        }
    }

    override fun createTables() {
        getDataSource()?.connection?.use { it.update(createCompanyTable(), mapOf()) {} }
        getDataSource()?.connection?.use { it.update(createOrderTable(), mapOf()) {} }
        getDataSource()?.connection?.use { it.update(createAssetTable(), mapOf()) {} }
    }

    private fun createCompanyTable() = StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append("simustock_company")
        .append(" (stockCode TEXT PRIMARY KEY,")
        .append("name TEXT NOT NULL,")
        .append("desc TEXT NOT NULL,")
        .append("currentStockNum INTEGER CHECK(currentStockNum >= 0),")
        .append("currentPrice DOUBLE CHECK(currentPrice >= 0.0),")
        .append("isListed BOOLEAN NOT NULL,")
        .append("riskLevel INTEGER NOT NULL)")
        .toString()

    private fun createOrderTable() = StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append("simustock_order")
        .append(" (orderNumber TEXT PRIMARY KEY,")
        .append("timestamp LONG NOT NULL,")
        .append("stockName TEXT NOT NULL,")
        .append("investorName TEXT NOT NULL,")
        .append("investorUUID TEXT NOT NULL,")
        .append("dealingAmount INTEGER CHECK(dealingAmount > 0),")
        .append("dealingPrice DOUBLE CHECK(dealingPrice > 0.0),")
        .append("isBuying BOOLEAN NOT NULL)")
        .toString()

    private fun createAssetTable() = StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append("simustock_asset")
        .append(" (stockCode TEXT NOT NULL UNIQUE,")
        .append("investorUUID TEXT NOT NULL UNIQUE,")
        .append("stockName TEXT NOT NULL,")
        .append("investorName TEXT NOT NULL,")
        .append("holdingNum INTEGER CHECK(holdingNum >= 0),")
        .append("holdingCost DOUBLE CHECK(holdingCost > 0.0),")
        .append("totalHoldingMarketCap DOUBLE CHECK(totalHoldingMarketCap > 0.0),")
        .append("currentDiff DOUBLE NOT NULL,")
        .append("diffRate DOUBLE NOT NULL,")
        .append("PRIMARY KEY(stockCode, investorUUID))")
        .toString()
}