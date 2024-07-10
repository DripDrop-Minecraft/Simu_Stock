package games.dripdrop.simustock.bean

data class Statics(
    // 玩家名称
    var investorName: String,
    // 玩家UUID
    var investorUUID: String,
    // 所有股票的持股总市值
    var marketCapOfAllStocks: Double,
    // 总盈亏
    var totalProfitAnLoss: Double
)
