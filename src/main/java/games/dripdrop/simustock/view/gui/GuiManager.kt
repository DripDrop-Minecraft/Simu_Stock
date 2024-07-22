package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.SystemService.getLocalization
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import org.bukkit.event.inventory.InventoryClickEvent

object GuiManager {
    private val mPageMap = mapOf(
        InventoryPage.HOMEPAGE to Homepage(),
        InventoryPage.COMPANIES to CompanyListPage(),
        InventoryPage.COMPANY_DETAIL to CompanyDetailPage(),
        InventoryPage.ANNOUNCEMENTS to AnnouncementPage(),
        InventoryPage.MY_ACCOUNT to AccountPage()
    )

    fun onClicked(event: InventoryClickEvent) {
        PluginLogManager.i("inventory click event passed, position = [${event.rawSlot}]")
        when (event.view.originalTitle) {
            getLocalization().exchangeName -> {
                getCurrentPage(InventoryPage.HOMEPAGE)?.onItemClicked(event)
            }

            getLocalization().titleOfCompanyList -> {
                getCurrentPage(InventoryPage.COMPANIES)?.onItemClicked(event)
            }

            getLocalization().titleOfCompanyDetail -> {
                getCurrentPage(InventoryPage.COMPANY_DETAIL)?.onItemClicked(event)
            }

            getLocalization().titleOfExchangeAnnouncement -> {
                getCurrentPage(InventoryPage.ANNOUNCEMENTS)?.onItemClicked(event)
            }

            getLocalization().titleOfMyAccount -> {
                getCurrentPage(InventoryPage.MY_ACCOUNT)?.onItemClicked(event)
            }

            else -> Unit
        }
    }

    fun getCurrentPage(page: InventoryPage): AbstractGuiManager? = mPageMap[page]

    fun getSpecifiedTitleList(title: String): Boolean {
        PluginLogManager.i("current inventory title: $title")
        return listOf(
            getLocalization().exchangeName, getLocalization().titleOfCompanyList,
            getLocalization().titleOfCompanyDetail, getLocalization().titleOfAnnouncementDetail,
            getLocalization().titleOfMyAccount, getLocalization().titleOfExchangeAnnouncement
        ).contains(title)
    }
}