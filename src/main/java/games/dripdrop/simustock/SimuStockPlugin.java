package games.dripdrop.simustock;

import games.dripdrop.simustock.utils.PluginLogManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimuStockPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginLogManager.INSTANCE.i("SimuStockPlugin is enabled now");
    }

    @Override
    public void onDisable() {
        PluginLogManager.INSTANCE.i("SimuStockPlugin is shutdown now");
    }
}
