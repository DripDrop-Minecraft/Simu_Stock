package games.dripdrop.simustock.presenter.interfaces

import games.dripdrop.simustock.model.bean.Order
import org.bukkit.entity.Player

interface IExchange {
    // 判断能否交易
    fun canTradeNow(): Boolean

    // 撮合交易订单
    fun completeOrders(vararg orders: Order)

    // 缓存交易订单
    fun cacheOrders(order: Order)

    // 撤销交易订单
    fun abandonOrders(orderNumber: String)

    // 清算交易数据
    fun saveTradeData()

    // 检查是否能进行交易
    fun checkTradingTime(player: Player, action: () -> Unit)
}