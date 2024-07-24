package games.dripdrop.simustock.presenter.interfaces

import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.SystemService
import games.dripdrop.simustock.view.gui.GuiManager
import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class AbstractGuiManager {
    protected var mInventoryCellsAmount = 54

    companion object {
        var data: HashMap<String, Any?> = hashMapOf()
    }

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

    open fun refreshData(inventory: Inventory) = Unit

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

    // 创建指定类型的容器
    fun createTypedInventory(type: InventoryType, title: String): Inventory {
        return Bukkit.createInventory(null, type, Component.text(title))
    }

    // 添加物品
    @Throws(IllegalArgumentException::class)
    fun addAnItem(inv: Inventory, position: Int, item: ItemStack) {
        if (position !in 0..53) {
            throw IllegalArgumentException("Invalid item position [$position]")
        }
        inv.setItem(position, item)
    }

    protected fun getLocalization() = SystemService.getLocalization()

    protected fun getConfig() = SystemService.getConfig()

    protected fun getSQLite() = SystemService.getSQLiteManager()

    protected fun getPlugin() = SystemService.getPlugin()

    protected fun createABook(title: String, author: String, vararg contents: String): Book {
        return Book.book(
            Component.text(title),
            Component.text(author),
            arrayListOf<Component>().apply {
                contents.onEach { add(Component.text(it)) }
            }
        )
    }

    protected fun createSimpleButton(material: Material, name: String, lore: List<String> = emptyList()): ItemStack {
        val list = arrayListOf<Component>().apply {
            lore.onEach { add(Component.text(it)) }
        }
        return ItemStack(material, 1).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text(name))
                if (list.isNotEmpty()) {
                    lore(list)
                }
            }
        }
    }

    protected fun Inventory.setBottomBar(startPosition: Int, showPageTurningButton: Boolean = true) {
        if (showPageTurningButton) {
            addAnItem(this, startPosition + 1, createPageTurningButton(false))
            addAnItem(this, startPosition + 7, createPageTurningButton(true))
        }
        addAnItem(this, startPosition + 3, createHomepageButton())
        addAnItem(this, startPosition + 5, createRefreshButton())
    }

    protected fun InventoryClickEvent.onBottomBarItemClicked(startPosition: Int, currentPosition: Int) {
        when (currentPosition) {
            startPosition + 1 -> changePage(true)
            startPosition + 3 -> toTargetPage(this, InventoryPage.HOMEPAGE)
            startPosition + 5 -> clickedInventory?.let { refreshData(it) }
            startPosition + 7 -> changePage()
        }
    }

    private fun createHomepageButton(): ItemStack {
        return createSimpleButton(Material.ORANGE_STAINED_GLASS_PANE, getLocalization().backToHomepage)
    }

    private fun createPageTurningButton(isToNext: Boolean): ItemStack {
        return createSimpleButton(
            if (isToNext) Material.GREEN_STAINED_GLASS_PANE else Material.RED_STAINED_GLASS_PANE,
            if (isToNext) getLocalization().goToNextPage else getLocalization().backToLastPage
        )
    }

    private fun createRefreshButton(): ItemStack {
        return createSimpleButton(Material.SPYGLASS, getLocalization().refresh)
    }
}