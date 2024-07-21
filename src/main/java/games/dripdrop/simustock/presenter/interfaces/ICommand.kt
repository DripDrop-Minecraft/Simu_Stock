package games.dripdrop.simustock.presenter.interfaces

import org.bukkit.command.CommandSender

interface ICommand {
    // 指令输入执行
    fun execute(sender: CommandSender, args: Array<out String>?)

    // 获取tab指令列表
    fun getTabCommandList(sender: CommandSender, args: Array<out String>?): MutableList<String>

    // 指令帮助
    fun CommandSender.help()

    // 打开图形界面
    fun CommandSender.openGui()

    // 导入公司列表
    fun CommandSender.importCompanies()

    // 发布公告
    fun CommandSender.publishAnnouncement(content: String)
}