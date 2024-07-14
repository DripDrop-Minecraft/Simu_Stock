package games.dripdrop.simustock.presenter.algorithm

import games.dripdrop.simustock.model.bean.Order
import games.dripdrop.simustock.presenter.interfaces.IAlgorithmManager

internal class AlgorithmManager : IAlgorithmManager {
    override fun getVolatilePriceForLowRiskLevel(): Double {
        TODO("Not yet implemented")
    }

    override fun getVolatilePriceForMiddleRiskLevel(): Double {
        TODO("Not yet implemented")
    }

    override fun getVolatilePriceForHighRiskLevel(): Double {
        TODO("Not yet implemented")
    }

    override fun getVolatilePriceForFraudRiskLevel(): Double {
        TODO("Not yet implemented")
    }

    override fun createOrderSequence(): Sequence<Order?> {
        TODO("Not yet implemented")
    }
}