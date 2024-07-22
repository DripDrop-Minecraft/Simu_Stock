package games.dripdrop.simustock.presenter.engine

import games.dripdrop.simustock.model.bean.Order
import games.dripdrop.simustock.model.bean.OrderKey
import games.dripdrop.simustock.model.constants.OrderState
import games.dripdrop.simustock.presenter.SystemService.getConfig
import games.dripdrop.simustock.presenter.SystemService.getLocalization
import games.dripdrop.simustock.presenter.database.SQLiteDatabaseManager
import games.dripdrop.simustock.presenter.interfaces.IExchange
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.text.SimpleDateFormat
import java.util.*

internal class SystemExchange(private val sqLiteDatabaseManager: SQLiteDatabaseManager) : IExchange {
    private val mComparatorForBuyer = Comparator<OrderKey> { o1, o2 ->
        // 买入订单先判断谁价高者再判断谁下单早
        return@Comparator if (0 != o1.orderPrice.compareTo(o2.orderPrice)) {
            o1.orderPrice.compareTo(o2.orderPrice)
        } else {
            o1.timestamp.compareTo(o2.timestamp)
        }
    }
    private val mComparatorForSeller = Comparator<OrderKey> { o1, o2 ->
        // 卖出订单先判断谁价低再判断谁下单早
        return@Comparator if (0 != o2.orderPrice.compareTo(o1.orderPrice)) {
            o2.orderPrice.compareTo(o1.orderPrice)
        } else {
            o1.timestamp.compareTo(o2.timestamp)
        }
    }
    private val mOrderBookForBuyer = TreeMap<OrderKey, Order>(mComparatorForBuyer)
    private val mOrderBookForSeller = TreeMap<OrderKey, Order>(mComparatorForSeller)

    override fun canTradeNow(): Boolean {
        return try {
            val weekDay = Calendar.getInstance()[Calendar.DAY_OF_WEEK]
            val sdf = SimpleDateFormat("HH:mm", Locale.PRC)
            val now = Calendar.getInstance().apply { time = sdf.parse(sdf.format(Date())) }
            val startTime = Calendar.getInstance().apply { time = sdf.parse(getConfig().openingTime) }
            val endTime = Calendar.getInstance().apply { time = sdf.parse(getConfig().endingTime) }
            Calendar.SUNDAY != weekDay && Calendar.SATURDAY != weekDay
                    && (startTime.before(now) && endTime.after(now))
                    && !getConfig().isMarketClosed
        } catch (e: Exception) {
            PluginLogManager.e("failed to judge trading time: ${e.localizedMessage}")
            e.printStackTrace()
            false
        }
    }

    override fun completeOrders(vararg orders: Order) {
        TODO("Not yet implemented")
    }

    override fun cacheOrders(order: Order) {
        if (order.isBuying) {
            mOrderBookForBuyer[OrderKey(order.timestamp, order.dealingPrice)] = order
        } else {
            mOrderBookForSeller[OrderKey(order.timestamp, order.dealingPrice)] = order
        }
    }

    override fun abandonOrders(orderNumber: String) {
        (mOrderBookForBuyer.values.find { it.orderNumber == orderNumber }
            ?: mOrderBookForSeller.values.find { it.orderNumber == orderNumber }
                )?.state = OrderState.ABANDONED.state
    }

    override fun saveTradeData() {
        TODO("Not yet implemented")
    }

    override fun checkTradingTime(player: Player, action: () -> Unit) {
        if (canTradeNow()) {
            action()
        } else {
            player.sendMessage(Component.text("${ChatColor.RED}${getLocalization().notTradingTime}"))
        }
    }
}