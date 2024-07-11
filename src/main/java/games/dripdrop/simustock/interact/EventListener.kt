package games.dripdrop.simustock.interact

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
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
}