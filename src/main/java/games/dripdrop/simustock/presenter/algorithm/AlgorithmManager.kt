package games.dripdrop.simustock.presenter.algorithm

import games.dripdrop.simustock.model.bean.RealStockIndex
import games.dripdrop.simustock.model.constants.RiskLevel
import games.dripdrop.simustock.presenter.SystemService
import games.dripdrop.simustock.presenter.interfaces.IAlgorithmManager
import games.dripdrop.simustock.presenter.network.RetrofitManager
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import java.util.concurrent.atomic.AtomicReference

internal class AlgorithmManager : IAlgorithmManager {
    private var mRiskStockIndexMap = AtomicReference(linkedMapOf<RiskLevel, List<RealStockIndex?>>())
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

    fun fetchStockIndexData() {
        PluginLogManager.i("fetch stock index data from real world...")
        RetrofitManager.getRealStockIndexes {
            arrayOf(
                RiskLevel.LOW, RiskLevel.MIDDLE, RiskLevel.HIGH, RiskLevel.FRAUD
            ).onEach { risk ->
                it.filter { getIndexNameWithRiskLevel(risk).contains(it?.code ?: "") }.apply {
                    mRiskStockIndexMap.get()[risk] = this
                }
            }
        }
    }

    private fun getIndexNameWithRiskLevel(riskLevel: RiskLevel): String {
        return when (riskLevel) {
            RiskLevel.LOW -> SystemService.getConfig().lowRiskKeywords
            RiskLevel.MIDDLE -> SystemService.getConfig().middleRisKeywords
            RiskLevel.HIGH -> SystemService.getConfig().highRiskKeywords
            else -> SystemService.getConfig().fraudRiskKeywords
        }
    }
}