package games.dripdrop.simustock.model.bean

data class Asset(
    // 所持股票的代码
    var stockCode: String,
    // 玩家UUID
    var investorUUID: String,
    // 所持股票名称
    var stockName: String,
    // 玩家名称
    var investorName: String,
    // 持股数量
    var holdingNum: Int,
    // 每股成本
    var holdingCost: Double,
    // 持股总市值
    var totalHoldingMarketCap: Double,
    // 盈亏
    var currentDiff: Double,
    // 盈亏比
    var diffRate: Double
)
