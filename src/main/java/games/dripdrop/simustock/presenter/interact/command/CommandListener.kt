package games.dripdrop.simustock.presenter.interact.command

import games.dripdrop.simustock.presenter.interfaces.ICommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

const val commandName = "stock"
const val commandAlias = "stk"

class CommandListener(private val executorImpl: ICommand) : CommandExecutor, TabExecutor {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> = executorImpl.getTabCommandList(sender, args)

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>?
    ): Boolean {
        return if (command.name.equals(commandName, true) || alias.equals(commandAlias, true)) {
            executorImpl.execute(sender, args)
            true
        } else {
            false
        }
    }
}