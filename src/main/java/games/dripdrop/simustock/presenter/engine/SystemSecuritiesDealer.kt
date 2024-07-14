package games.dripdrop.simustock.presenter.engine

import games.dripdrop.simustock.model.bean.FluctuationReason
import games.dripdrop.simustock.model.bean.Order
import games.dripdrop.simustock.presenter.database.SQLiteDatabaseManager
import games.dripdrop.simustock.presenter.interfaces.ISecuritiesDealer
import net.milkbowl.vault.economy.Economy
import org.bukkit.entity.Player

internal class SystemSecuritiesDealer(
    val sqLiteDatabaseManager: SQLiteDatabaseManager, val economy: Economy
) : ISecuritiesDealer {

    override fun calculateCommission(order: Order, rate: Double): Double {
        TODO("Not yet implemented")
    }

    override fun calculateStampTax(order: Order, rate: Double): Double {
        TODO("Not yet implemented")
    }

    override fun checkPlayerBalance(player: Player) {
        TODO("Not yet implemented")
    }

    override fun withdrawPlayer(player: Player, cost: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun depositPlayer(player: Player, income: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun createFluctuationReason(stockCode: String, desc: String, isPositive: Boolean): FluctuationReason {
        return FluctuationReason(stockCode, desc, isPositive, System.currentTimeMillis())
    }
}