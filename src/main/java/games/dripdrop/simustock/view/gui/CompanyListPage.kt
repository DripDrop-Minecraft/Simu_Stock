package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.model.bean.Company
import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import games.dripdrop.simustock.presenter.utils.CoroutineManager
import games.dripdrop.simustock.presenter.utils.TextFormatManager.createDateTime
import net.kyori.adventure.text.Component
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
            when (event.rawSlot) {
                mCompanyLimit + 1 -> changePage(true)
                mCompanyLimit + 3 -> toTargetPage(event, InventoryPage.HOMEPAGE)
                mInventoryCellsAmount - 4 -> event.clickedInventory?.let { loadCompanies(it) }
                mInventoryCellsAmount - 2 -> changePage()
            }
        }
    }

    override fun initView(player: HumanEntity) {
        createInventory(mInventoryCellsAmount, getLocalization().titleOfCompanyList).apply {
            loadCompanies(this)
            setButtons(this)
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

    private fun setButtons(inventory: Inventory) {
        addAnItem(
            inventory, mCompanyLimit + 1,
            createButton(Material.RED_STAINED_GLASS_PANE, getLocalization().backToLastPage)
        )
        addAnItem(
            inventory, mCompanyLimit + 3,
            createButton(Material.ORANGE_STAINED_GLASS_PANE, getLocalization().backToHomepage)
        )
        addAnItem(
            inventory, mInventoryCellsAmount - 4,
            createButton(Material.SPYGLASS, getLocalization().refresh)
        )
        addAnItem(
            inventory, mInventoryCellsAmount - 2,
            createButton(Material.GREEN_STAINED_GLASS_PANE, getLocalization().goToNextPage)
        )
    }

    private fun loadCompanies(inventory: Inventory) {
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

    private fun createButton(material: Material, name: String): ItemStack {
        return ItemStack(material, 1).apply {
            itemMeta = itemMeta.apply { displayName(Component.text(name)) }
        }
    }

    private fun createCompanyIcon(company: Company): ItemStack {
        return ItemStack(Material.MAP, 1).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text(company.name))
                lore(
                    listOf(
                        Component.text(""),
                        Component.text(
                            "${ChatColor.DARK_AQUA}${getLocalization().stockCode}：${company.stockCode}"
                        ),
                        Component.text(
                            "${ChatColor.AQUA}${getLocalization().listedTime}：${createDateTime(date = Date(company.listedTime))}"
                        ),
                        Component.text(
                            "${ChatColor.RED}${getLocalization().currentPrice}：${company.currentPrice}"
                        )
                    )
                )
            }
        }
    }
}