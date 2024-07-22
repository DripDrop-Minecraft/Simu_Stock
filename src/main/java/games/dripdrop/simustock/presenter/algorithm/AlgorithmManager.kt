package games.dripdrop.simustock.presenter.algorithm

import games.dripdrop.simustock.presenter.interfaces.IAlgorithmManager

internal class AlgorithmManager : IAlgorithmManager {
    private val mFraud = FraudRiskPriceFluctuationImpl()
    private val mHigh = HighRiskPriceFluctuationImpl()
    private val mLow = LowRiskPriceFluctuationImpl()
    private val mMid = MidRiskPriceFluctuationImpl()

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
}