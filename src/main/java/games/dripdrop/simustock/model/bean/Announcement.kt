package games.dripdrop.simustock.model.bean

data class Announcement(
    // 交易所公告发布时间戳
    var timestamp: Long = System.currentTimeMillis(),
    // 公告标题
    var title: String,
    // 公告内容
    var content: String
)