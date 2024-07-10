package games.dripdrop.simustock.interfaces

import games.dripdrop.simustock.bean.Order
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
}