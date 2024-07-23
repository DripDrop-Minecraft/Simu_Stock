package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryType

class BankPage : AbstractGuiManager() {
    override fun initView(player: HumanEntity) {
        createTypedInventory(InventoryType.DISPENSER, getLocalization().titleOfBank).apply {
            // TODO
            player.openInventory(this)
        }
    }
}