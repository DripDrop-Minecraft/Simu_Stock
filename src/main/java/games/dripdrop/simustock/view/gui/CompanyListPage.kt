package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.model.bean.Company
import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import games.dripdrop.simustock.presenter.utils.CoroutineManager
import games.dripdrop.simustock.presenter.utils.TextFormatManager.createDateTime
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.concurrent.atomic.AtomicReferenceArray

class CompanyListPage : AbstractGuiManager() {
    private val mCompanyLimit = 45
    private val mCompanies = AtomicReferenceArray<Company>(mCompanyLimit)

    override fun onItemClicked(event: InventoryClickEvent) {
        if (event.rawSlot in 0..<mCompanyLimit) {
            data["CurrentCompany"] = mCompanies.get(event.rawSlot)
            toTargetPage(event, InventoryPage.TRADING)
        } else {
            event.onBottomBarItemClicked(mCompanyLimit, event.rawSlot)
        }
    }

    override fun initView(player: HumanEntity) {
        createInventory(mInventoryCellsAmount, getLocalization().titleOfCompanyList).apply {
            refreshData(this)
            setBottomBar(mCompanyLimit)
            player.openInventory(this)
        }
    }

    override fun changePage(isPrevious: Boolean) {
        if (isPrevious) {
            // TODO
        } else {
            // TODO
        }
    }

    override fun refreshData(inventory: Inventory) {
        CoroutineManager.runOnIOThread(javaClass.simpleName) {
            getSQLite().queryCompanyByStockCode {
                it.take(mCompanyLimit).onEachIndexed { index, company ->
                    mCompanies.set(index, company)
                    CoroutineManager.runOnUiThread(getPlugin()) {
                        addAnItem(inventory, index, createCompanyIcon(company))
                    }
                }
            }
        }
    }

    private fun createCompanyIcon(company: Company): ItemStack {
        return createSimpleButton(
            Material.MAP,
            company.name,
            listOf(
                "",
                "${ChatColor.DARK_AQUA}${getLocalization().stockCode}：${company.stockCode}",
                "${ChatColor.AQUA}${getLocalization().listedTime}：${createDateTime(date = Date(company.listedTime))}",
                "${ChatColor.RED}${getLocalization().currentPrice}：${company.currentPrice}"
            )
        )
    }
}