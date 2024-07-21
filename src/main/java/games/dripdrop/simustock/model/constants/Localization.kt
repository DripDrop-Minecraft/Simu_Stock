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
    val announcementFromExchange: String = "来自交易所的公告"
)
