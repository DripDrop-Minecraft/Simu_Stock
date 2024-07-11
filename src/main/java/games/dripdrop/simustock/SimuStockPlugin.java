package games.dripdrop.simustock;

import games.dripdrop.simustock.center.SystemService;
import games.dripdrop.simustock.interact.EventListener;
import games.dripdrop.simustock.utils.PluginLogManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimuStockPlugin extends JavaPlugin {

    private final Listener mEventListener = new EventListener();
    private final PluginLogManager mLogger = PluginLogManager.INSTANCE;
    private final SystemService mService = SystemService.INSTANCE;

    @Override
    public void onEnable() {
        mLogger.i("SimuStockPlugin is enabled now");
        mService.runSimulatedStockMarket();
        mLogger.i("register event listener...");
        getServer().getPluginManager().registerEvents(mEventListener, this);
    }

    @Override
    public void onDisable() {
        mLogger.i("SimuStockPlugin is shutdown now");
        mService.stopSimulatedStockMarket();
    }
}
