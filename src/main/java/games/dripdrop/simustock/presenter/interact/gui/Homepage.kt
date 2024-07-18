package games.dripdrop.simustock.presenter.interact.gui

import games.dripdrop.simustock.model.bean.GuiItem
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class Homepage : AbstractGuiManager() {
    private val mItemMap: Map<Int, GuiItem> = mapOf(
        1 to createGuideButton(),
        3 to createCompanyListButton(),
        5 to createAnnouncementButton(),
        7 to createMyAccountButton()
    )

    override fun onItemClicked(event: InventoryClickEvent) {
        mItemMap[event.rawSlot]?.let {
            dispatchAction(it.material, (event.whoClicked as Player))
        }
    }

    fun getItemMap(): Map<Int, GuiItem> = mItemMap

    private fun dispatchAction(material: Material, entity: Player) {
        when (material) {
            Material.DIAMOND -> {
                // TODO
            }

            Material.BELL -> {
                // TODO
            }

            Material.ENDER_PEARL -> {
                // TODO
            }

            else -> Unit
        }
    }

    private fun createGuideButton(): GuiItem {
        return GuiItem(
            Material.COMPASS,
            "交易规则",
            listOf(Component.text(""))
        )
    }

    private fun createCompanyListButton(): GuiItem {
        return GuiItem(
            Material.DIAMOND,
            "股票列表",
            listOf(Component.text(""))
        )
    }

    private fun createAnnouncementButton(): GuiItem {
        return GuiItem(
            Material.BELL,
            "交易所公告",
            listOf(Component.text(""))
        )
    }

    private fun createMyAccountButton(): GuiItem {
        return GuiItem(
            Material.ENDER_PEARL,
            "我的账户",
            listOf(Component.text(""))
        )
    }
}