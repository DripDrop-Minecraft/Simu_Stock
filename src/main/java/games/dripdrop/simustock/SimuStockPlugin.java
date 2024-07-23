package games.dripdrop.simustock;

import games.dripdrop.simustock.presenter.SystemService;
import games.dripdrop.simustock.presenter.interfaces.ICommand;
import games.dripdrop.simustock.presenter.utils.PluginLogManager;
import games.dripdrop.simustock.view.command.CommandExecutorImpl;
import games.dripdrop.simustock.view.command.CommandListener;
import games.dripdrop.simustock.view.command.CommandListenerKt;
import games.dripdrop.simustock.view.command.EventListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimuStockPlugin extends JavaPlugin {

    private final Listener mEventListener = new EventListener();
    private final ICommand mCommandExecutorImpl = new CommandExecutorImpl();
    private final CommandListener mCommandListener = new CommandListener(mCommandExecutorImpl);
    private final PluginLogManager mLogger = PluginLogManager.INSTANCE;
    private final SystemService mService = SystemService.INSTANCE;

    @Override
    public void onEnable() {
        mLogger.i("SimuStockPlugin is enabled now");
        mService.runSimulatedStockMarket(this);
        registerListeners();
    }

    @Override
    public void onDisable() {
        mLogger.i("SimuStockPlugin is shutdown now");
        mService.stopSimulatedStockMarket();
    }

    private void registerListeners() {
        mLogger.i("register event listener...");
        getServer().getPluginManager().registerEvents(mEventListener, this);
        mLogger.i("register command listener...");
        PluginCommand mCommandName = getCommand(CommandListenerKt.commandName);
        mLogger.d("command name is not null: " + (mCommandName != null));
        if (mCommandName != null) {
            mCommandName.setExecutor(mCommandListener);
            mCommandName.setTabCompleter(mCommandListener);
        }
        PluginCommand mCommandAlias = getCommand(CommandListenerKt.commandAlias);
        mLogger.d("command alias is not null: " + (mCommandAlias != null));
        if (mCommandAlias != null) {
            mCommandAlias.setExecutor(mCommandListener);
            mCommandAlias.setTabCompleter(mCommandListener);
        }
    }
}
