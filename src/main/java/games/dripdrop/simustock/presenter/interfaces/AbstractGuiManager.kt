package games.dripdrop.simustock.presenter.interfaces

import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.view.gui.GuiManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class AbstractGuiManager {
    protected var mInventoryCellsAmount = 54

    // 处理物品点击事件
    open fun onItemClicked(event: InventoryClickEvent) = Unit

    // 初始化页面
    open fun initView(player: HumanEntity) = Unit

    // 跳转页面
    open fun toTargetPage(event: InventoryClickEvent, page: InventoryPage) {
        event.whoClicked.apply {
            leaveInventory(this)
            GuiManager.getCurrentPage(page)?.initView(this)
        }
    }

    // 翻页
    open fun changePage(isPrevious: Boolean = false) = Unit

    // 关闭容器
    open fun leaveInventory(player: HumanEntity) {
        player.closeInventory()
    }

    // 创建容器
    @Throws(IllegalArgumentException::class)
    fun createInventory(itemAmount: Int, title: String): Inventory {
        if (itemAmount !in 9..54 || itemAmount % 9 != 0) {
            throw IllegalArgumentException("Invalid item amount [$itemAmount]")
        }
        return Bukkit.createInventory(null, itemAmount, Component.text(title))
    }

    // 添加物品
    @Throws(IllegalArgumentException::class)
    fun addAnItem(inv: Inventory, position: Int, item: ItemStack) {
        if (position !in 0..53) {
            throw IllegalArgumentException("Invalid item position [$position]")
        }
        inv.setItem(position, item)
    }
}