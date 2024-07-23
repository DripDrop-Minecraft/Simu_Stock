package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

class AccountPage : AbstractGuiManager() {

    override fun onItemClicked(event: InventoryClickEvent) {
        TODO("Not yet implemented")
    }

    override fun initView(player: HumanEntity) {
        createTypedInventory(InventoryType.HOPPER, getLocalization().titleOfMyAccount).apply {
            // TODO
            player.openInventory(this)
        }
    }
}