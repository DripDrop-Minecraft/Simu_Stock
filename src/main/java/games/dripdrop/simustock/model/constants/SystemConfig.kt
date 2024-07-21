package games.dripdrop.simustock.model.constants

data class SystemConfig(
    // 交易佣金费率
    var commissionRate: Double = 0.0015,
    // 印花税率
    var stampDutyRate: Double = 0.001,
    // 最低交易费用
    var minimumTransactionFee: Double = 5.0,
    // 最高交易费率
    var maximumTransactionFeeRate: Double = 0.005,
    // 有权限玩家
    var administrators: List<String> = arrayListOf("CONSOLE"),
    // 数据库账号
    var databaseAccount: String = "",
    // 数据库账号密码
    var databasePassword: String = "",
    // 是否休市
    var isMarketClosed: Boolean = false
)