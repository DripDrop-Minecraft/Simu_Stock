package games.dripdrop.simustock.interfaces

import games.dripdrop.simustock.bean.Company
import games.dripdrop.simustock.bean.Order
import games.dripdrop.simustock.bean.ShareHoldingInfo
import games.dripdrop.simustock.bean.Statics
import org.bukkit.entity.Player
import java.sql.Connection
import java.sql.ResultSet

interface IDatabase {
    // 初始化数据库连接
    fun initDatabaseConnection(driver: String, url: String): Connection?

    // 创建数据库
    fun createDatabase()

    // 执行SQL语句
    fun executeSQLStatement(sql: String): ResultSet?

    // 创建公司
    fun createCompanies(vararg companies: Company): Boolean

    // 通过股票代码删除公司
    fun deleteCompanyByCode(stockCode: String): Boolean

    // 查询所有公司信息
    fun queryAllCompanies(): List<Company?>

    // 通过名称查询特定公司信息
    fun querySpecifiedCompanyByName(stockName: String): List<Company?>

    // 通过股票代码查询特定公司信息
    fun querySpecifiedCompanyByCode(stockCode: String): Company?

    // 修改特定公司信息
    fun updateSpecifiedCompanyInfo(company: Company): Boolean

    // 保存交易订单
    fun createOrders(vararg orders: Order)

    // 删除所有交易订单
    fun deleteAllOrders()

    // 查询某一玩家名下所有订单
    fun queryOrdersByPlayer(player: Player): List<Order?>

    // 通过名称查询某一玩家所持特定公司股票情况
    fun queryShareHoldingInfoByPlayerAndStockName(player: Player, stockName: String): ShareHoldingInfo?

    // 通过代码查询某一玩家所持特定公司股票情况
    fun queryShareHoldingInfoByPlayerAndStockCode(player: Player, stockCode: String): ShareHoldingInfo?

    // 查询某一玩家名下持股情况
    fun queryStaticsByPlayer(player: Player): Statics?

    // 查询某一玩家名下特定股票代码订单
    fun queryOrdersByPlayerAndStockCode(player: Player, stockCode: String): List<Order?>

    // 通过订单号查询特定订单
    fun queryOrderByNumber(orderNumber: String): Order?
}