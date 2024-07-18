package games.dripdrop.simustock.presenter.interact.gui

import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import org.bukkit.event.inventory.InventoryClickEvent

object GuiManager {
    private val mPageMap: Map<InventoryPage, AbstractGuiManager> = mapOf(
        InventoryPage.HOMEPAGE to Homepage()
    )

    fun onClicked(event: InventoryClickEvent) {
        PluginLogManager.i("inventory click event passed")
        handlePages(event.rawSlot)
    }

    fun getSpecifiedPage(flag: InventoryPage): AbstractGuiManager? = mPageMap[flag]

    private fun handlePages(position: Int) {
        PluginLogManager.i("current clicked position is [$position]")
        // TODO
    }
}