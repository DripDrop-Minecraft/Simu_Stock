package games.dripdrop.simustock.bean

data class Investor(
    // 玩家名称
    var name: String,
    // 玩家UUID
    var uuid: String,
    // 所持股票
    var stocks: List<ShareHolding>
)
