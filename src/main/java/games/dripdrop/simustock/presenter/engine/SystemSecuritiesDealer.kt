package games.dripdrop.simustock.presenter.engine

import games.dripdrop.simustock.model.bean.Company
import games.dripdrop.simustock.model.bean.FluctuationReason
import games.dripdrop.simustock.model.bean.Order
import games.dripdrop.simustock.presenter.SystemService.getConfig
import games.dripdrop.simustock.presenter.SystemService.getLocalization
import games.dripdrop.simustock.presenter.interfaces.ISecuritiesDealer
import games.dripdrop.simustock.presenter.utils.UniqueIDManager
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Color
import org.bukkit.entity.Player

internal class SystemSecuritiesDealer(private val economy: Economy) : ISecuritiesDealer {

    override fun calculateCommission(order: Order, rate: Double): Double {
        val total = order.dealingAmount * order.dealingPrice
        val current = total * rate
        return if ((0.0).compareTo(total) >= 0) {
            // 如果手数或价格任意一个值不大于0，则不可能收取任何费用
            0.0
        } else if (getConfig().minimumTransactionFee.compareTo(current) > 0) {
            // 计算出来的交易费率低于设定的最低费用时，按最低费用收取
            getConfig().minimumTransactionFee
        } else if (getConfig().maximumTransactionFeeRate.compareTo(rate) <= 0) {
            // 传入的交易费率超过设定的最大费率时，按设定的最大费率收取
            total * getConfig().maximumTransactionFeeRate
        } else {
            current
        }
    }

    override fun calculateStampTax(order: Order, rate: Double): Double {
        val total = order.dealingAmount * order.dealingPrice
        return if (order.isBuying || (0.0).compareTo(total) > 0) {
            // 卖出股票不收取印花税，买入才收
            0.0
        } else {
            total * rate
        }
    }

    override fun checkPlayerBalance(player: Player, transaction: Double): Boolean {
        // 交易额不超过账户余额才能执行买入操作
        return (economy.getBalance(player).compareTo(transaction) >= 0).apply {
            if (!this) {
                player.sendMessage(
                    "${Color.GREEN}[SimuStock] ${Color.RED}${getLocalization().insufficientFunds}"
                )
            }
        }
    }

    override fun issueTransaction(player: Player, order: Order) {
        val transaction = order.dealingAmount * order.dealingPrice
        if (order.isBuying) {
            if (checkPlayerBalance(player, transaction)) {
                economy.withdrawPlayer(player, transaction).transactionFailed(player)
            }
        } else {
            economy.depositPlayer(player, transaction).transactionFailed(player)
        }
    }

    override fun createFluctuationReason(company: Company, desc: String, isPositive: Boolean): FluctuationReason {
        return FluctuationReason(company, desc, isPositive, System.currentTimeMillis())
    }

    @Throws(IllegalArgumentException::class)
    override fun createOrder(
        player: Player,
        company: Company,
        dealingAmount: Int,
        dealingPrice: Double,
        isBuying: Boolean
    ): Order {
        if (dealingAmount <= 0 || (0.0).compareTo(dealingPrice) >= 0) {
            player.sendMessage("${Color.GREEN}[SimuStock] ${Color.RED}${getLocalization().invalidOrder}")
            throw IllegalArgumentException("Invalid dealingAmount[$dealingAmount] or dealingPrice[$dealingPrice]")
        }
        return Order(
            UniqueIDManager.createOrderNumber(),
            System.currentTimeMillis(),
            company.name,
            company.stockCode,
            player.name,
            player.identity().uuid().toString(),
            dealingAmount, dealingPrice, isBuying
        )
    }

    private fun EconomyResponse.transactionFailed(player: Player): Boolean {
        return transactionSuccess().apply {
            if (this) {
                player.sendMessage(
                    "${Color.GREEN}[SimuStock] ${Color.RED}${getLocalization().failedTransaction}${errorMessage}"
                )
            }
        }
    }
}