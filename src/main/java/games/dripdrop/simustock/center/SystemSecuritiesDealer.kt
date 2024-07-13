package games.dripdrop.simustock.center

import games.dripdrop.simustock.bean.Order
import games.dripdrop.simustock.database.SQLiteDatabaseManager
import games.dripdrop.simustock.interfaces.ISecuritiesDealer
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
}