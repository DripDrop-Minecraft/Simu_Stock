package games.dripdrop.simustock.interfaces

import games.dripdrop.simustock.bean.Company
import games.dripdrop.simustock.bean.Order

interface IExchange {
    // 判断能否交易
    fun canTradeNow(): Boolean

    // 撮合交易订单
    fun completeOrders(vararg orders: Order)

    // 缓存交易订单
    fun cacheOrders(vararg orders: Order)

    // 撤销交易订单
    fun abandonOrders(vararg orderNumbers: String)

    // 公司上市
    fun listCompany(vararg companies: Company)

    // 公司退市
    fun delistCompany(vararg stockCodes: String)

    // 清算交易数据
    fun saveTradeData()

    // 发布公告
    fun announce(content: String)
}