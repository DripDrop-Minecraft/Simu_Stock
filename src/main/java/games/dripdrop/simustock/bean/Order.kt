package games.dripdrop.simustock.bean

data class Order(
    // 订单编号
    var orderNumber: String,
    // 交易时间戳
    var timestamp: Long,
    // 交易股票公司名
    var stockName: String,
    // 交易股票代码
    var stockCode: String,
    // 交易玩家名
    var investorName: String,
    // 交易玩家UUID
    var investorUUID: String,
    // 交易手数
    var dealingAmount: Int,
    // 每股交易单价
    var dealingPrice: Float,
    // 买入或卖出
    var isBuying: Boolean
)
