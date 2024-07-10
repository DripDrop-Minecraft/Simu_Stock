package games.dripdrop.simustock.interact

import games.dripdrop.simustock.interfaces.ICommand
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

}