package games.dripdrop.simustock.presenter.interact.command

import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.presenter.SystemService.getConfig
import games.dripdrop.simustock.presenter.interact.gui.GuiManager
import games.dripdrop.simustock.presenter.interact.gui.Homepage
import games.dripdrop.simustock.presenter.interfaces.ICommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

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

    override fun CommandSender.openGui() {
        if (this is Player) {
            GuiManager.getSpecifiedPage(InventoryPage.HOMEPAGE)?.let {
                it.createInventory(9, getConfig().exchangeName).apply {
                    ((it as Homepage).getItemMap()).onEach { item ->
                        it.addAnItem(this, item.key, it.createItem(item.value))
                    }
                    openInventory(this)
                }
            }
        }
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