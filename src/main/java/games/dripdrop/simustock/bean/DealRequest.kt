package games.dripdrop.simustock.bean

data class DealRequest(
    // 交易时间戳
    var timestamp: Long,
    // 交易股票代码
    var stockCode: String,
    // 交易玩家UUID
    var investorUUID: String,
    // 交易手数
    var dealingAmmount: Int,
    // 当前每股单价
    var currentPrice: Float,
    // 买入或卖出
    var isBuying: Boolean,
)
