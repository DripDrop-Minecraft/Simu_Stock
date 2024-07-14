package games.dripdrop.simustock;

import games.dripdrop.simustock.presenter.SystemService;
import games.dripdrop.simustock.presenter.interact.CommandListener;
import games.dripdrop.simustock.presenter.interact.CommandListenerKt;
import games.dripdrop.simustock.presenter.interact.EventListener;
import games.dripdrop.simustock.presenter.utils.PluginLogManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SimuStockPlugin extends JavaPlugin {

    private final Listener mEventListener = new EventListener();
    private final CommandListener mCommandListener = new CommandListener();
    private final PluginLogManager mLogger = PluginLogManager.INSTANCE;
    private final SystemService mService = SystemService.INSTANCE;
    private final PluginCommand mCommandName = Objects.requireNonNull(getCommand(CommandListenerKt.commandName));
    private final PluginCommand mCommandAlias = Objects.requireNonNull(getCommand(CommandListenerKt.commandAlias));

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
        mCommandName.setExecutor(mCommandListener);
        mCommandName.setTabCompleter(mCommandListener);
        mCommandAlias.setExecutor(mCommandListener);
        mCommandAlias.setTabCompleter(mCommandListener);
    }
}
