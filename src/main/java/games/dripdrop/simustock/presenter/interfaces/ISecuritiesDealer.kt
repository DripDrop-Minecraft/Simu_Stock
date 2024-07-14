package games.dripdrop.simustock.presenter.interfaces

import games.dripdrop.simustock.model.bean.Company
import games.dripdrop.simustock.model.bean.FluctuationReason
import games.dripdrop.simustock.model.bean.Order
import org.bukkit.entity.Player

interface ISecuritiesDealer {
    // 计算交易佣金费用
    fun calculateCommission(order: Order, rate: Double): Double

    // 计算印花税
    fun calculateStampTax(order: Order, rate: Double): Double

    // 检查玩家账户余额
    fun checkPlayerBalance(player: Player, transaction: Double): Boolean

    // 处理交易金额
    fun issueTransaction(player: Player, order: Order)

    // 制造利好或利空消息
    fun createFluctuationReason(company: Company, desc: String, isPositive: Boolean): FluctuationReason

    // 创建交易订单
    fun createOrder(
        player: Player,
        company: Company,
        dealingAmount: Int,
        dealingPrice: Double,
        isBuying: Boolean,
    ): Order
}