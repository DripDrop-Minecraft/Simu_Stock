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
        InventoryPage.TRADING to StockTradingPage(),
        InventoryPage.ANNOUNCEMENTS to AnnouncementPage(),
        InventoryPage.MY_ACCOUNT to AccountPage(),
        InventoryPage.BANK to BankPage()
    )

    fun onClicked(event: InventoryClickEvent) {
        PluginLogManager.i("position = [${event.rawSlot}], title = [${event.view.originalTitle}]")
        when (event.view.originalTitle) {
            getLocalization().exchangeName -> {
                getCurrentPage(InventoryPage.HOMEPAGE)?.onItemClicked(event)
            }

            getLocalization().titleOfCompanyList -> {
                getCurrentPage(InventoryPage.COMPANIES)?.onItemClicked(event)
            }

            getLocalization().titleOfCompanyDetail -> {
                getCurrentPage(InventoryPage.TRADING)?.onItemClicked(event)
            }

            getLocalization().titleOfAnnouncementDetail -> {
                getCurrentPage(InventoryPage.ANNOUNCEMENTS)?.onItemClicked(event)
            }

            getLocalization().titleOfMyAccount -> {
                getCurrentPage(InventoryPage.MY_ACCOUNT)?.onItemClicked(event)
            }

            getLocalization().titleOfBank -> {
                getCurrentPage(InventoryPage.BANK)?.onItemClicked(event)
            }

            else -> Unit
        }
    }

    fun getCurrentPage(page: InventoryPage): AbstractGuiManager? = mPageMap[page]

    fun getSpecifiedTitleList(title: String): Boolean {
        PluginLogManager.d("current inventory title: $title")
        return listOf(
            getLocalization().exchangeName, getLocalization().titleOfCompanyList,
            getLocalization().titleOfCompanyDetail, getLocalization().titleOfAnnouncementDetail,
            getLocalization().titleOfMyAccount, getLocalization().titleOfExchangeAnnouncement,
            getLocalization().titleOfBank
        ).contains(title)
    }
}