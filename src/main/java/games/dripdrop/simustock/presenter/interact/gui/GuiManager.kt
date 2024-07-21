package games.dripdrop.simustock.presenter.interact.gui

import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.SystemService.getLocalization
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import org.bukkit.event.inventory.InventoryClickEvent

object GuiManager {
    private val mPageMap = mapOf(
        InventoryPage.HOMEPAGE to Homepage()
    )

    fun onClicked(event: InventoryClickEvent) {
        PluginLogManager.i("inventory click event passed")
        PluginLogManager.i("current clicked position is [${event.rawSlot}]")
        when (event.view.originalTitle) {
            getLocalization().exchangeName -> {
                getCurrentPage(InventoryPage.HOMEPAGE)?.onItemClicked(event)
            }

            getLocalization().titleOfCompanyList -> {
                getCurrentPage(InventoryPage.COMPANIES)?.onItemClicked(event)
            }

            getLocalization().titleOfExchangeAnnouncement -> {
                getCurrentPage(InventoryPage.ANNOUNCEMENTS)?.onItemClicked(event)
            }

            else -> Unit
        }
    }

    fun getCurrentPage(page: InventoryPage): AbstractGuiManager? = mPageMap[page]

    fun getSpecifiedTitleList(title: String): Boolean {
        PluginLogManager.i("current inventory title: $title")
        return listOf(
            getLocalization().exchangeName, getLocalization().titleOfCompanyList,
            getLocalization().titleOfMyAccount, getLocalization().titleOfExchangeAnnouncement
        ).contains(title)
    }
}