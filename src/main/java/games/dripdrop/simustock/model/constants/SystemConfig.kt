package games.dripdrop.simustock.model.constants

data class SystemConfig(
    // 交易佣金费率
    var commissionRate: Double = 0.0015,
    // 印花税率
    var stampDutyRate: Double = 0.001,
    // 服务器交易所名称
    var exchangeName: String = "DripDrop证券交易所",
    // 服务器券商名称
    var securitiesDealerName: String = "DripDrop信投",
    // 有权限玩家
    var administrators: List<String> = arrayListOf("CONSOLE"),
    // 数据库账号
    var databaseAccount: String = "",
    // 数据库账号密码
    var databasePassword: String = "",
    // 是否休市
    var isMarketClosed: Boolean = false
)