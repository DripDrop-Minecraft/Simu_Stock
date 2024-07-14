package games.dripdrop.simustock.presenter.interfaces

import org.bukkit.command.CommandSender

interface ICommand {
    // 指令输入执行
    fun execute(sender: CommandSender, args: Array<out String>?)

    // 获取tab指令列表
    fun getTabCommandList(sender: CommandSender, args: Array<out String>?): MutableList<String>

    // 指令帮助
    fun help()

    // 重载配置
    fun reloadConfig()

    // 发送消息给玩家
    fun sendMessageToPlayer(content: String)

    // 导入公司列表
    fun importCompanies()

    // 获取公司信息
    fun queryCompaniesByStockCode(vararg stockCodes: String)
}