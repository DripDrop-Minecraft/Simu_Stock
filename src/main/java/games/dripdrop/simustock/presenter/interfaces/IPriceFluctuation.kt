package games.dripdrop.simustock.presenter.interfaces

import games.dripdrop.simustock.model.bean.Company
import games.dripdrop.simustock.model.bean.FluctuationReason

interface IPriceFluctuation {
    // 提升或降低股价
    fun increaseOrDecreaseStockPrice(stockCode: String, isIncremental: Boolean): Double

    // 接收外部股价波动因素
    fun receiveFluctuationReason(reason: FluctuationReason): Boolean

    // 根据公司内部因素计算股价波动率
    fun calculateFluctuationRate(company: Company): Double

    //
}