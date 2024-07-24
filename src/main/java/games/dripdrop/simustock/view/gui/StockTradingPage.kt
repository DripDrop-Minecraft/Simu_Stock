package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

class StockTradingPage : AbstractGuiManager() {
    private val mStartPosition = 18

    init {
        mInventoryCellsAmount = 27
    }

    override fun onItemClicked(event: InventoryClickEvent) {
        if (event.rawSlot in mStartPosition..<mInventoryCellsAmount) {
            when (event.rawSlot) {
                mStartPosition + 3, mStartPosition + 5 -> {
                    event.onBottomBarItemClicked(mStartPosition, event.rawSlot)
                }
            }
        } else {
            Unit
        }
    }

    override fun initView(player: HumanEntity) {
        createTypedInventory(InventoryType.BARREL, getLocalization().titleOfCompanyDetail).apply {
            setBottomBar(mStartPosition, false)
            // TODO: 设置买入卖出界面
            player.openInventory(this)
        }
    }
}