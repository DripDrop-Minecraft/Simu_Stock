package games.dripdrop.simustock.model.bean

data class OrderKey(
    // 订单时间戳
    val timestamp: Long,
    // 订单价格
    val orderPrice: Double
)
