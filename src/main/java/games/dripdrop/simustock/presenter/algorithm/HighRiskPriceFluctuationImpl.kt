package games.dripdrop.simustock.presenter.algorithm

import games.dripdrop.simustock.model.bean.Company
import games.dripdrop.simustock.model.bean.FluctuationReason
import games.dripdrop.simustock.presenter.interfaces.IPriceFluctuation

class HighRiskPriceFluctuationImpl : IPriceFluctuation {
    override fun increaseOrDecreaseStockPrice(stockCode: String, isIncremental: Boolean): Double {
        TODO("Not yet implemented")
    }

    override fun receiveFluctuationReason(reason: FluctuationReason): Boolean {
        TODO("Not yet implemented")
    }

    override fun calculateFluctuationRate(company: Company): Double {
        TODO("Not yet implemented")
    }
}