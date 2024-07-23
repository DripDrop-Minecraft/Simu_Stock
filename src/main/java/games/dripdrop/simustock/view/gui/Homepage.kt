package games.dripdrop.simustock.view.gui

import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.interfaces.AbstractGuiManager
import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

/**
 * 首页提供交易规则、股票列表、交易所公告以及玩家账户入口
 * */
class Homepage : AbstractGuiManager() {
    private val mIconList = listOf(
        createGuideButton(),
        createCompanyListButton(),
        createAnnouncementButton(),
        createMyAccountButton(),
        createBankButton()
    )

    override fun onItemClicked(event: InventoryClickEvent) {
        if (event.rawSlot in 0..mIconList.lastIndex) {
            dispatchAction(mIconList[event.rawSlot].type, event)
        } else {
            Unit
        }
    }

    override fun initView(player: HumanEntity) {
        createTypedInventory(InventoryType.HOPPER, getLocalization().exchangeName).apply {
            mIconList.onEachIndexed { index, item -> addAnItem(this, index, item) }
            player.openInventory(this)
        }
    }

    private fun dispatchAction(material: Material, event: InventoryClickEvent) {
        when (material) {
            Material.BOOK -> event.whoClicked.openBook(createGuidance())
            Material.CAKE -> toTargetPage(event, InventoryPage.COMPANIES)
            Material.BELL -> toTargetPage(event, InventoryPage.ANNOUNCEMENTS)
            Material.PLAYER_HEAD -> toTargetPage(event, InventoryPage.MY_ACCOUNT)
            Material.WRITABLE_BOOK -> toTargetPage(event, InventoryPage.BANK)
            else -> Unit
        }
    }

    private fun createGuidance(): Book {
        return createABook(
            getLocalization().titleOfGuidance,
            getLocalization().exchangeName,
            """
                ${ChatColor.DARK_AQUA}欢迎访问${getLocalization().exchangeName}
                
                ${ChatColor.DARK_AQUA}在正式开始您的炒股之旅前，请仔细阅读本交易所的交易规则。
                
                ${ChatColor.RED}【免责声明】
                
                ${ChatColor.RED}1.本功能仅供娱乐，与一切真实人物、事件、团体、组织均无关联；
                ${ChatColor.RED}2.本服不为任何玩家在现实世界所进行的任何投资理财活动提供任何参考建议。
                
            """.trimIndent(),
            """
                ${ChatColor.GREEN}【交易时间】${ChatColor.BLACK}现实世界周一到周五，每天${getConfig().openingTime}~${getConfig().endingTime}，节假日等情形休市；
                
                ${ChatColor.GREEN}【交易费用】${ChatColor.BLACK}每笔交易收取交易佣金和印花税，其中：
                
                ${ChatColor.BLACK}1）交易佣金最低收取5.0游戏币，最高不超过交易金额的${getConfig().maximumTransactionFeeRate * 100}%；
                ${ChatColor.BLACK}2）当前每笔交易佣金费率为${getConfig().commissionRate * 100}%；
                ${ChatColor.BLACK}3）当前每笔交易向${ChatColor.RED}买方${ChatColor.BLACK}征收印花税的税率为${getConfig().stampDutyRate * 100}%。
            """.trimIndent(),
            """
                ${ChatColor.GREEN}【买入数量】${ChatColor.BLACK}买入股票时最低股数为100股（即“一手”），每次买入必须是100的整数倍；
                ${ChatColor.GREEN}【卖出数量】${ChatColor.BLACK}卖出股票没有“一手”的数量限制，且最低可卖出股数为1股；
                ${ChatColor.GREEN}【成交规则】${ChatColor.BLACK}当申报的买入价≥当前股价时方可成交，当申报的卖出价≤当前股价时方可成交；
                ${ChatColor.GREEN}【交易间隔】${ChatColor.BLACK}T日买入的股票，最早可在当日卖出，即T+0交易，其中T为交易日当天。
            """.trimIndent()
        )
    }

    private fun createIcon(material: Material, title: String): ItemStack {
        return ItemStack(material, 1).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text(title))
            }
        }
    }

    private fun createGuideButton(): ItemStack {
        return createIcon(Material.BOOK, getLocalization().titleOfGuidance)
    }

    private fun createCompanyListButton(): ItemStack =
        createIcon(Material.CAKE, getLocalization().titleOfCompanyList)

    private fun createAnnouncementButton(): ItemStack =
        createIcon(Material.BELL, getLocalization().titleOfExchangeAnnouncement)

    private fun createMyAccountButton(): ItemStack =
        createIcon(Material.PLAYER_HEAD, getLocalization().titleOfMyAccount)

    private fun createBankButton(): ItemStack =
        createIcon(Material.WRITABLE_BOOK, getLocalization().titleOfBank)
}