# BungeeServerTracker
Track server data via bungeecord plugin messaging in a bukkit plugin.

# How to use
This is an example POM that uses BungeeServerTracker.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.birthdates</groupId>
    <artifactId>BungeeServerTrackerTest</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <!-- REQUIRED !-->
    <repositories>
        <!-- Add the BungeeServerTracker repo via GitHub !-->
        <repository>
            <id>BungeeServerTracker-repo</id>
            <url>https://raw.githubusercontent.com/birthdates/BungeeServerTracker/repository/</url>
        </repository>
    </repositories>

    <dependencies>
        <!-- Add the BungeeServerTracker dependency and scope compile !-->
        <dependency>
            <groupId>com.birthdates</groupId>
            <artifactId>BungeeServerTracker</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
</project>
```
This is an example class that would track two server's data (Factions, Skyblock)
```java
import com.birthdates.bungeeservertracker.BungeeServerTracker;
import com.birthdates.bungeeservertracker.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class BungeeServerTrackerTest extends JavaPlugin {

    private BungeeServerTracker playerTracker;

    public void onEnable() {
        playerTracker = new BungeeServerTracker();

        playerTracker.init(this, 3);
        playerTracker.startTracking("factions", "127.0.0.1:25566");
        playerTracker.startTracking("skyblock", "127.0.0.1:25567");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            ServerData skyblockData = playerTracker.getServerData("skyblock");
            Bukkit.getLogger().log(Level.WARNING, "Skyblock is currently " + (skyblockData.isOnline() ? "online with " + skyblockData.getPlayers() + " players" : "offline") + ".");
        }, 0L, 20L);
    }

}
```
