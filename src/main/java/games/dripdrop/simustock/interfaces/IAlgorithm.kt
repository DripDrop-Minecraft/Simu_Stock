package games.dripdrop.simustock.interfaces

import games.dripdrop.simustock.bean.Order

interface IAlgorithm {
    // 低风险股价格波动算法
    fun getVolatilePriceForLowRiskLevel(): Double

    // 中风险股价波动算法
    fun getVolatilePriceForMiddleRiskLevel(): Double

    // 高风险股价波动算法
    fun getVolatilePriceForHighRiskLevel(): Double

    // 欺诈型股价波动算法
    fun getVolatilePriceForFraudRiskLevel(): Double

    // 交易订单定序算法
    fun createOrderSequence(): Sequence<Order?>

    // 交易订单撮合算法
    fun makeOrdersMatched(buy: Order, sell: Order): Boolean
}