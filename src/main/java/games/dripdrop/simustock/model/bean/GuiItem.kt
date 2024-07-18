package games.dripdrop.simustock.model.bean

import net.kyori.adventure.text.Component
import org.bukkit.Material

data class GuiItem(
    val material: Material,
    val displayName: String,
    val lore: List<Component>
)
