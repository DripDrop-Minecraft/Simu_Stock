package games.dripdrop.simustock.presenter.interact.gui

import games.dripdrop.simustock.presenter.SystemService.getLocalization
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class Homepage : AbstractGuiManager() {
    private val mItemMap: HashMap<Int, ItemStack> = hashMapOf(
        1 to createGuideButton(),
        3 to createCompanyListButton(),
        5 to createAnnouncementButton(),
        7 to createMyAccountButton()
    )

    override fun onItemClicked(event: InventoryClickEvent) {
        mItemMap[event.rawSlot]?.let { dispatchAction(it.type, event) }
    }

    fun getItemMap(): Map<Int, ItemStack> = mItemMap

    private fun dispatchAction(material: Material, event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        when (material) {
            Material.COMPASS -> player.openBook(createGuidance())
            Material.DIAMOND -> {
                player.sendMessage(Component.text("查看股票列表"))
            }

            Material.BELL -> {
                player.sendMessage(Component.text("查看交易所公告"))
            }

            Material.PLAYER_HEAD -> {
                player.sendMessage(Component.text("查看我的账户"))
            }

            else -> Unit
        }
    }

    private fun createGuidance(): ItemStack {
        return ItemStack(Material.WRITTEN_BOOK, 1)
    }

    private fun createIcon(material: Material, title: String): ItemStack {
        return ItemStack(material, 1).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text(title))
            }
        }
    }

    private fun createGuideButton(): ItemStack = createIcon(Material.BOOK, getLocalization().titleOfGuidance)

    private fun createCompanyListButton(): ItemStack =
        createIcon(Material.DIAMOND, getLocalization().titleOfCompanyList)

    private fun createAnnouncementButton(): ItemStack =
        createIcon(Material.BELL, getLocalization().titleOfExchangeAnnouncement)

    private fun createMyAccountButton(): ItemStack =
        createIcon(Material.PLAYER_HEAD, getLocalization().titleOfMyAccount)
}