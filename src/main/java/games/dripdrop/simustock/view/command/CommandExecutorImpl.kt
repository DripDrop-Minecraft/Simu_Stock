package games.dripdrop.simustock.view.command

import games.dripdrop.simustock.model.bean.Announcement
import games.dripdrop.simustock.model.bean.Company
import games.dripdrop.simustock.model.constants.InventoryPage
import games.dripdrop.simustock.model.constants.PluginCommands.*
import games.dripdrop.simustock.model.constants.PluginFile
import games.dripdrop.simustock.presenter.SystemService
import games.dripdrop.simustock.presenter.SystemService.getConfig
import games.dripdrop.simustock.presenter.SystemService.getLocalization
import games.dripdrop.simustock.presenter.SystemService.getRootPath
import games.dripdrop.simustock.presenter.interfaces.ICommand
import games.dripdrop.simustock.presenter.utils.JsonManager
import games.dripdrop.simustock.presenter.utils.PluginLogManager
import games.dripdrop.simustock.presenter.utils.TextFormatManager
import games.dripdrop.simustock.view.gui.GuiManager
import games.dripdrop.simustock.view.gui.Homepage
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandExecutorImpl : ICommand {
    private val mCommandListForOp = listOf(
        HELP.command,
        GUI.command,
        IMPORT_COMPANIES.command,
        ANNOUNCE.command
    )
    private val mCommandListForNormalPlayers = listOf(
        HELP.command,
        GUI.command
    )

    override fun execute(sender: CommandSender, args: Array<out String>?) {
        when {
            HELP.command == args?.first() -> sender.help()
            GUI.command == args?.first() -> sender.openGui()
            IMPORT_COMPANIES.command == args?.first() -> sender.importCompanies()
            ANNOUNCE.command == args?.first() -> sender.publishAnnouncement(args)
        }
    }

    override fun getTabCommandList(sender: CommandSender, args: Array<out String>?): MutableList<String> {
        return if (sender.isHighPriority()) {
            mCommandListForOp.toMutableList()
        } else {
            mCommandListForNormalPlayers.toMutableList()
        }
    }

    override fun CommandSender.help() {
        if (this is Player) {
            sendMessage(
                StringBuilder("${ChatColor.GREEN}[${getLocalization().exchangeName}]\n")
                    .append("${ChatColor.YELLOW}${getLocalization().help}\n")
                    .apply {
                        (if (isHighPriority()) mCommandListForOp else mCommandListForNormalPlayers).onEach {
                            append("${ChatColor.YELLOW}/${commandName} $it - ${handleHint(it)}\n")
                        }
                    }.toString()
            )
        }
    }

    override fun CommandSender.openGui() {
        if (this is Player) {
            GuiManager.getCurrentPage(InventoryPage.HOMEPAGE)?.let {
                it.createInventory(9, getLocalization().exchangeName).apply {
                    (it as Homepage).getItemMap().onEach { (position, item) ->
                        PluginLogManager.i("position: $position, item: ${item.type.name}")
                        it.addAnItem(this, position, item)
                    }
                    openInventory(this)
                }
            }
        }
    }

    override fun CommandSender.importCompanies() {
        sendMessage(getLocalization().importingCompanies)
        SystemService.getSQLiteManager().insertCompanies(
            JsonManager.getObjectList<Company>(getRootPath(), PluginFile.COMPANY_LIST_FILE)
        )
        sendMessage(getLocalization().companiesImported)
    }

    override fun CommandSender.publishAnnouncement(args: Array<out String>) {
        val tag = "${TextFormatManager.createDateTime()} ${getLocalization().announcementFromExchange}"
        val title = args.getOrNull(1)
        val content = args.getOrNull(2)
        PluginLogManager.i("announcement [$title] published by [$name]")
        if (title.isNullOrEmpty() || content.isNullOrEmpty()) {
            sendMessage("${ChatColor.RED}${getLocalization().announcementException}")
            return
        }
        SystemService.getSQLiteManager().insertAnnouncement(Announcement(title = title, content = content))
        Bukkit.broadcast(Component.text("${ChatColor.GREEN}[$tag] ${ChatColor.YELLOW}$content"))
    }

    private fun CommandSender.isHighPriority(): Boolean {
        return isOp || getConfig().administrators.contains(name)
    }

    private fun handleHint(command: String): String {
        return when (command) {
            HELP.command -> getLocalization().descForHelp
            GUI.command -> getLocalization().descForGui
            IMPORT_COMPANIES.command -> getLocalization().descForImportCompanies
            ANNOUNCE.command -> getLocalization().descForAnnounce
            else -> "NULL"
        }
    }
}