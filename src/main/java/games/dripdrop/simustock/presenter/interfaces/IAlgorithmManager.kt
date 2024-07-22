package games.dripdrop.simustock.presenter.interfaces

interface IAlgorithmManager {
    // 低风险股价格波动算法
    fun getVolatilePriceForLowRiskLevel(): Double

    // 中风险股价波动算法
    fun getVolatilePriceForMiddleRiskLevel(): Double

    // 高风险股价波动算法
    fun getVolatilePriceForHighRiskLevel(): Double

    // 欺诈型股价波动算法
    fun getVolatilePriceForFraudRiskLevel(): Double
}