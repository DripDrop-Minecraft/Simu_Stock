package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.model.bean.Company
import games.dripdrop.simustock.presenter.SystemService
import games.dripdrop.simustock.presenter.SystemService.getLocalization
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import games.dripdrop.simustock.presenter.utils.CoroutineManager
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import games.dripdrop.simustock.presenter.utils.TextFormatManager.createDateTime
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

class CompanyListPage : AbstractGuiManager() {

    override fun onItemClicked(event: InventoryClickEvent) {
        PluginLogManager.i("${javaClass.simpleName} get click event")
    }

    override fun initView(player: HumanEntity) {
        createInventory(mInventoryCellsAmount, getLocalization().titleOfCompanyList).apply {
            refreshCompanies(this)
            player.openInventory(this)
        }
    }

    private fun refreshCompanies(inventory: Inventory) {
        CoroutineManager.runOnIOThread(javaClass.simpleName) {
            SystemService.getSQLiteManager().queryCompanyByStockCode {
                it.onEachIndexed { index, company ->
                    CoroutineManager.runOnUiThread(SystemService.getPlugin()) {
                        addAnItem(inventory, index, createCompanyIcon(company))
                    }
                }
            }
        }
    }

    private fun createCompanyIcon(company: Company): ItemStack {
        return ItemStack(Material.PAPER, 1).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text(company.name))
                lore(
                    listOf(
                        Component.text(""),
                        Component.text("${ChatColor.DARK_AQUA}股票代码：${company.stockCode}"),
                        Component.text("${ChatColor.AQUA}上市时间：${createDateTime(date = Date(company.listedTime))}"),
                        Component.text("${ChatColor.RED}当前股价：${company.currentPrice}")
                    )
                )
            }
        }
    }
}