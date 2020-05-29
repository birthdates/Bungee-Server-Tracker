import com.birthdates.bungeeplayertracker.BungeePlayerTracker;
import com.birthdates.bungeeplayertracker.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class BungeePlayerTrackerTest extends JavaPlugin {

    private BungeePlayerTracker playerTracker;

    public void onEnable() {
        playerTracker = new BungeePlayerTracker();

        playerTracker.init(this, 3);
        playerTracker.startTracking("factions", "127.0.0.1:25566");
        playerTracker.startTracking("skyblock", "127.0.0.1:25567");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            ServerData skyblockData = playerTracker.getServerData("skyblock");
            Bukkit.getLogger().log(Level.WARNING, "Skyblock is currently " + (skyblockData.isOnline() ? "online with " + skyblockData.getPlayers() + " players" : "offline") + ".");
        }, 0L, 20L);
    }

}
