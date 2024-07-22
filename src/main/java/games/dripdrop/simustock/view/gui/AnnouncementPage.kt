package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.model.bean.Announcement
import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.SystemService
import games.dripdrop.simustock.presenter.SystemService.getLocalization
import games.dripdrop.simustock.presenter.SystemService.getPlugin
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import games.dripdrop.simustock.presenter.utils.CoroutineManager
import games.dripdrop.simustock.presenter.utils.TextFormatManager
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class AnnouncementPage : AbstractGuiManager() {
    // 展示近期发布公告的数量上限
    private val mAnnounceAmount = 18

    init {
        mInventoryCellsAmount = 27
    }

    override fun onItemClicked(event: InventoryClickEvent) {
        if (event.rawSlot in 18..26 && Material.ORANGE_STAINED_GLASS_PANE == event.currentItem?.type) {
            toTargetPage(event, InventoryPage.HOMEPAGE)
        }
    }

    override fun initView(player: HumanEntity) {
        createInventory(mInventoryCellsAmount, getLocalization().titleOfAnnouncementDetail).apply {
            refreshAllAnnouncements(this)
            player.openInventory(this)
        }
    }

    private fun createGlassPane(): ItemStack {
        return ItemStack(Material.ORANGE_STAINED_GLASS_PANE, 1).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text(getLocalization().backToLastPage))
            }
        }
    }

    private fun refreshAllAnnouncements(inventory: Inventory) {
        repeat(mInventoryCellsAmount - mAnnounceAmount) {
            addAnItem(inventory, mAnnounceAmount + it, createGlassPane())
        }
        CoroutineManager.runOnIOThread(InventoryPage.ANNOUNCEMENTS.name) {
            SystemService.getSQLiteManager().queryAllAnnouncements {
                it.take(mAnnounceAmount).onEachIndexed { index, announcement ->
                    CoroutineManager.runOnUiThread(getPlugin()) {
                        addAnItem(inventory, index, createPaperItem(announcement))
                    }
                }
            }
        }
    }

    private fun createPaperItem(announcement: Announcement): ItemStack {
        return ItemStack(Material.PAPER, 1).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text(announcement.title))
                lore(createFormattedContent(TextFormatManager.formatLongText(announcement.content)))
            }
        }
    }

    private fun createFormattedContent(content: String): List<Component> {
        val list = arrayListOf<Component>()
        content.split(Regex("\\n")).onEach {
            list.add(Component.text("${ChatColor.YELLOW}$it"))
        }
        return list
    }
}