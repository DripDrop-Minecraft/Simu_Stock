package games.dripdrop.simustock.presenter.interfaces

import games.dripdrop.simustock.model.bean.FluctuationReason
import games.dripdrop.simustock.model.bean.Order
import org.bukkit.entity.Player

interface ISecuritiesDealer {
    // 计算交易佣金费用
    fun calculateCommission(order: Order, rate: Double): Double

    // 计算印花税
    fun calculateStampTax(order: Order, rate: Double): Double

    // 检查玩家账户余额
    fun checkPlayerBalance(player: Player)

    // 扣除费用
    fun withdrawPlayer(player: Player, cost: Double): Boolean

    // 返还费用
    fun depositPlayer(player: Player, income: Double): Boolean

    // 制造利好或利空消息
    fun createFluctuationReason(stockCode: String, desc: String, isPositive: Boolean): FluctuationReason
}