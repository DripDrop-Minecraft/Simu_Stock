package games.dripdrop.simustock.bean

enum class RiskLevel(val risk: Int) {
    // 欺诈型
    FRAUD(3),

    // 高风险
    HIGH(2),

    // 中等风险
    MIDDLE(1),

    // 低风险
    LOW(0)
}