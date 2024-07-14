package games.dripdrop.simustock.model.bean

data class FluctuationReason(
    val stockCode: String,
    val desc: String,
    // 是否为利好因素
    val isPositive: Boolean,
    // 发布时间
    val publishTime: Long
)
