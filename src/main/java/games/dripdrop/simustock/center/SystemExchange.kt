package games.dripdrop.simustock.center

import games.dripdrop.simustock.bean.Company
import games.dripdrop.simustock.bean.Order
import games.dripdrop.simustock.bean.RiskLevel
import games.dripdrop.simustock.interfaces.IExchange

internal class SystemExchange : IExchange {
    override fun handleOrder(vararg orders: Order) {
        TODO("Not yet implemented")
    }

    override fun revokeOrder(vararg orderNumbers: String) {
        TODO("Not yet implemented")
    }

    override fun listCompany(vararg companies: Company) {
        TODO("Not yet implemented")
    }

    override fun delistCompany(vararg stockCodes: String) {
        TODO("Not yet implemented")
    }

    override fun deleteCompany(vararg stockCodes: String) {
        TODO("Not yet implemented")
    }

    override fun updateCompanyName(stockCode: String, stockName: String) {
        TODO("Not yet implemented")
    }

    override fun updateCompanyDesc(stockCode: String, desc: String) {
        TODO("Not yet implemented")
    }

    override fun updateCompanyStockNum(stockCode: String, stockNum: Int) {
        TODO("Not yet implemented")
    }

    override fun updateCompanyStockPrice(stockCode: String, stockPrice: Double) {
        TODO("Not yet implemented")
    }

    override fun updateCompanyRiskLevel(stockCode: String, riskLevel: RiskLevel) {
        TODO("Not yet implemented")
    }

    override fun announce(content: String) {
        TODO("Not yet implemented")
    }
}