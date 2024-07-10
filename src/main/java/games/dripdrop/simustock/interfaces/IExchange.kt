package games.dripdrop.simustock.interfaces

import games.dripdrop.simustock.bean.Company
import games.dripdrop.simustock.bean.Order
import games.dripdrop.simustock.bean.RiskLevel

interface IExchange {
    // 处理交易订单
    fun handleOrder(vararg orders: Order)

    // 撤销交易订单
    fun revokeOrder(vararg orderNumbers: String)

    // 公司上市
    fun listCompany(vararg companies: Company)

    // 公司退市
    fun delistCompany(vararg stockCodes: String)

    // 公司注销
    fun deleteCompany(vararg stockCodes: String)

    // 修改公司名称
    fun updateCompanyName(stockCode: String, stockName: String)

    // 修改公司描述
    fun updateCompanyDesc(stockCode: String, desc: String)

    // 修改公司当前股票可购买数量
    fun updateCompanyStockNum(stockCode: String, stockNum: Int)

    // 修改公司当前每股单价
    fun updateCompanyStockPrice(stockCode: String, stockPrice: Double)

    // 修改公司风险等级
    fun updateCompanyRiskLevel(stockCode: String, riskLevel: RiskLevel)

    // 发布公告
    fun announce(content: String)
}