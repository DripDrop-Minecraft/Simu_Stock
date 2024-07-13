package games.dripdrop.simustock.bean

data class Localization(
    val noPermission: String = "你没有权限执行此操作！",
    val help: String = "你能使用的命令如下列所示：\n",
    val reloadingConfig: String = "正在重新加载配置……",
    val configReloaded: String = "配置已重新加载！",
    val importingCompanies: String = "正在导入公司……",
    val companiesImported: String = "公司已导入！"
)
