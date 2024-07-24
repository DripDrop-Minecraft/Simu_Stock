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
    var isMarketClosed: Boolean = false,
    // 开盘时间
    var openingTime: String = "09:30",
    // 收盘时间
    var endingTime: String = "20:00",
    // 低风险股指
    var lowRiskKeywords: String = "DJI,IXIC,INX",
    // 中风险股指
    var middleRisKeywords: String = "gzGSPTSE,UKX,DAX30,FCHI,N225,ENSEX,E3X,FTSEMIB,AEX",
    // 高风险股指
    var highRiskKeywords: String = "MXX,RTS,PSI20,IBEX,ASE,KS11,TWII,FTSTI,FBMKLCI,S51,NZSE50FG,TOP40",
    // 欺诈性股指
    var fraudRiskKeywords: String = "000001,399001,XIN9,HSI"
)