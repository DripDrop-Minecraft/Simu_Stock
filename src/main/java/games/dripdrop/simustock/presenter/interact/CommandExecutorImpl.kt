package games.dripdrop.simustock.presenter.interact

import games.dripdrop.simustock.presenter.interfaces.ICommand
import org.bukkit.command.CommandSender

class CommandExecutorImpl : ICommand {

    override fun execute(sender: CommandSender, args: Array<out String>?) {
        TODO("Not yet implemented")
    }

    override fun getTabCommandList(sender: CommandSender, args: Array<out String>?): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun CommandSender.help() {
        TODO("Not yet implemented")
    }

    override fun CommandSender.reloadConfig() {
        TODO("Not yet implemented")
    }

    override fun CommandSender.sendMessageToPlayer(content: String) {
        TODO("Not yet implemented")
    }

    override fun CommandSender.importCompanies() {
        TODO("Not yet implemented")
    }

    override fun CommandSender.queryCompaniesByStockCode(vararg stockCodes: String) {
        TODO("Not yet implemented")
    }

}