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
    val titleOfCompanyDetail: String = "股票详情",
    val titleOfAnnouncementDetail: String = "公告详情",
    val announcementException: String = "请输入公告标题和公告内容，两者以空格分隔",
    val notTradingTime: String = "当前不在交易时间段内！"
)
