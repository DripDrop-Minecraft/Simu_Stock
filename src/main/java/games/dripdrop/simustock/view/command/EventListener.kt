package games.dripdrop.simustock.view.command

import games.dripdrop.simustock.view.gui.GuiManager
import games.dripdrop.simustock.view.gui.GuiManager.getSpecifiedTitleList
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent

class EventListener : Listener {

    @EventHandler
    fun onPlayerJoined(event: PlayerJoinEvent) {
        // TODO
    }

    @EventHandler
    fun onPlayerChat(event: AsyncChatEvent) {
        // TODO
    }

    @EventHandler
    fun onPlayerClickInventory(event: InventoryClickEvent) {
        if (getSpecifiedTitleList(event.view.originalTitle)) {
            // 判断容器标题是否为特定内容，从而禁止玩家从容器内拿取物品
            event.isCancelled = true
            GuiManager.onClicked(event)
        }
    }
}