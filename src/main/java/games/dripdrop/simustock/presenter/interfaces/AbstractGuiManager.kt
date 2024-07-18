package games.dripdrop.simustock.presenter.interfaces

import games.dripdrop.simustock.model.bean.GuiItem
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class AbstractGuiManager {
    // 处理物品点击事件
    abstract fun onItemClicked(event: InventoryClickEvent)

    // 创建容器
    @Throws(IllegalArgumentException::class)
    fun createInventory(itemAmount: Int, title: String): Inventory {
        if (itemAmount !in 9..54 || itemAmount % 9 != 0) {
            throw IllegalArgumentException("Invalid item amount [$itemAmount]")
        }
        return Bukkit.createInventory(null, itemAmount, Component.text(title))
    }

    // 创建物品
    fun createItem(item: GuiItem): ItemStack {
        return ItemStack(item.material).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text(item.displayName))
                lore(item.lore)
            }
        }
    }

    // 添加物品
    @Throws(IllegalArgumentException::class)
    fun addAnItem(inv: Inventory, position: Int, item: ItemStack) {
        if (position !in 0..53) {
            throw IllegalArgumentException("Invalid item position [$position]")
        }
        inv.addItem(item)
        inv.setItem(position, item)
    }

    // 关闭容器
    fun leaveInventory(player: Player, event: InventoryClickEvent) {
        player.closeInventory()
        event.isCancelled = true
    }
}