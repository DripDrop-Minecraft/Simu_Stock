package games.dripdrop.simustock.bean

data class Company(
    // 股票代码
    var stockCode: String,
    // 公司名称
    var name: String,
    // 公司描述
    var desc: String,
    // 当前可购买股票数量
    val currentStockNum: Int,
    // 当前每股单价
    var currentPrice: Double,
    // 上市状态
    var isListed: Boolean,
    // 风险等级
    var riskLevel: Int

)
