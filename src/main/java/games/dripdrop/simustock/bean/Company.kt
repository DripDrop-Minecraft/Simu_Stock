package games.dripdrop.simustock.bean

data class Company(
    // 公司名称
    var name: String,
    // 股票代码
    var stockCode: String,
    // 公司描述
    var desc: String,
    // 当前股票份额
    val currentStockNum: Int,
    // 当前每股单价
    var currentPrice: Float,
    // 上市状态
    var isListed: Boolean
)
