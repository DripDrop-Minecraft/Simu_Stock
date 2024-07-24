package games.dripdrop.simustock.model.bean

data class RealStockIndex(
    // 指数名称
    val name: String,
    // 指数代码
    val code: String,
    // 最新价
    val zxj: Double
)
