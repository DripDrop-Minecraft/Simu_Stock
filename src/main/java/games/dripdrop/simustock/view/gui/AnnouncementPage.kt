package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.model.bean.Announcement
import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import games.dripdrop.simustock.presenter.utils.CoroutineManager
import games.dripdrop.simustock.presenter.utils.TextFormatManager
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class AnnouncementPage : AbstractGuiManager() {
    // 展示近期发布公告的数量上限
    private val mAnnounceAmount = 18

    override fun onItemClicked(event: InventoryClickEvent) {
        if (event.rawSlot in mAnnounceAmount..<mInventoryCellsAmount) {
            event.onBottomBarItemClicked(mAnnounceAmount, event.rawSlot)
        } else {
            Unit
        }
    }

    override fun initView(player: HumanEntity) {
        createTypedInventory(InventoryType.BARREL, getLocalization().titleOfAnnouncementDetail).apply {
            setBottomBar(mAnnounceAmount)
            refreshData(this)
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
        CoroutineManager.runOnIOThread(InventoryPage.ANNOUNCEMENTS.name) {
            getSQLite().queryAllAnnouncements {
                it.take(mAnnounceAmount).onEachIndexed { index, announcement ->
                    CoroutineManager.runOnUiThread(getPlugin()) {
                        addAnItem(inventory, index, createPaperItem(announcement))
                    }
                }
            }
        }
    }

    private fun createPaperItem(announcement: Announcement): ItemStack {
        return createSimpleButton(
            Material.PAPER,
            announcement.title,
            createFormattedContent(announcement.content)
        )
    }

    private fun createFormattedContent(content: String): List<String> {
        return arrayListOf<String>().apply {
            TextFormatManager.formatLongText(content).split(Regex("\\n")).onEach {
                add("${ChatColor.YELLOW}$it")
            }
        }
    }
}