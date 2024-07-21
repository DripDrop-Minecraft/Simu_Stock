package games.dripdrop.simustock.model.constants

data class Localization(
    val noPermission: String = "你没有权限执行此操作！",
    val help: String = "你能使用的命令如下列所示：\n",
    val reloadingConfig: String = "正在重新加载配置……",
    val configReloaded: String = "配置已重新加载！",
    val importingCompanies: String = "正在导入公司……",
    val companiesImported: String = "公司已导入！",
    val insufficientFunds: String = "账户余额不足，无法交易！",
    val failedTransaction: String = "交易失败！原因：",
    val invalidOrder: String = "无效订单！",
    val descForHelp: String = "查询你能使用的命令",
    val descForGui: String = "打开操作界面",
    val descForImportCompanies: String = "导入公司列表",
    val descForAnnounce: String = "发布公告",
    val announcementFromExchange: String = "来自交易所的公告",
    val titleOfGuidance: String = "交易规则",
    val titleOfCompanyList: String = "股票列表",
    val titleOfExchangeAnnouncement: String = "交易所公告",
    val titleOfMyAccount: String = "我的账户",
    val exchangeName: String = "DripDrop证券交易所",
    val securitiesDealerName: String = "DripDrop信投",
    val contentOfGuidance: String = """
        =======本服不提供任何针对现实世界的理财投资建议，请勿对号入座！=======
        
        1. 交易时间：周一到周五每天09:30 - 16:00，节假日等情况休市；
        2. 交易费用：目前收取交易佣金和印花税；
        3. 买入数量：买入股票时最低股数为100（即“一手”），往上加则必须是100的整数倍；
        4. 卖出数量：卖出股票则没有“一手”限制，最低卖出股数为1股；
        4. 成交规则：当申报的买入价≥当前股价时方可成交；当申报的卖出价≤当前股价时方可成交；
        5. 交易间隔：当日买入的股票，最早可在次日卖出，其中T表示交易日。
        
        ======股市有风险，投资需谨慎！======
    """.trimIndent()
)
