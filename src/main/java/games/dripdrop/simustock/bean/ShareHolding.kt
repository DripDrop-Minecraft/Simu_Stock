package games.dripdrop.simustock.bean

data class ShareHolding(
    // 所持股票的代码
    var stockCode: String,
    // 玩家UUID
    var investorUUID: String,
    // 持股数量
    var holdingNum: Int,
    // 持股总市值
    var totalHoldingMarketCap: Float,
    // 每股成本
    var holdingCost: Float,
    // 盈亏
    var currentDiff: Float,
    // 盈亏比
    var diffRate: Float
)
