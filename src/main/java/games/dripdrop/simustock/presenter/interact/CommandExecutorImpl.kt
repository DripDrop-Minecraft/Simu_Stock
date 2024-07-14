package games.dripdrop.simustock.presenter.interact

import games.dripdrop.simustock.presenter.interfaces.ICommand
import org.bukkit.command.CommandSender

internal class CommandExecutorImpl : ICommand {
    override fun execute(sender: CommandSender, args: Array<out String>?) {
        TODO("Not yet implemented")
    }

    override fun getTabCommandList(sender: CommandSender, args: Array<out String>?): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun help() {
        TODO("Not yet implemented")
    }

    override fun reloadConfig() {
        TODO("Not yet implemented")
    }

    override fun sendMessageToPlayer(content: String) {
        TODO("Not yet implemented")
    }

    override fun importCompanies() {
        TODO("Not yet implemented")
    }

    override fun queryCompaniesByStockCode(vararg stockCodes: String) {
        TODO("Not yet implemented")
    }

}