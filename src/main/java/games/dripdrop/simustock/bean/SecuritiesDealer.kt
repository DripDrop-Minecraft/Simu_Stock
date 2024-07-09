package games.dripdrop.simustock.bean

data class SecuritiesDealer(
    // 券商名称
    var name: String,
    // 券商描述
    var desc: String,
    // 佣金比例
    var commissionRate: String,
    // 印花税率
    var stampDutyRate: String
)
