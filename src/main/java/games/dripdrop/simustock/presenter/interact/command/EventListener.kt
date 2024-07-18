package games.dripdrop.simustock.presenter.interact.command

import games.dripdrop.simustock.presenter.interact.gui.GuiManager
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
        GuiManager.onClicked(event)
    }
}