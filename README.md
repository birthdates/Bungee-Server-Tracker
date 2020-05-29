# BungeePlayerTracker
Track server data with bungeecord plugin messaging in a bukkit plugin.

# How to use
This is an example POM that uses BungeePlayerTracker.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.birthdates</groupId>
    <artifactId>BungeePlayerTrackerTest</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <!-- REQUIRED !-->
    <repositories>
        <!-- Add the BungeePlayerTracker repo via GitHub !-->
        <repository>
            <id>bungeeplayertracker-repo</id>
            <url>https://raw.githubusercontent.com/birthdates/BungeePlayerTracker/repository/</url>
        </repository>
    </repositories>

    <dependencies>
        <!-- Add the BungeePlayerTracker dependency and scope compile !-->
        <dependency>
            <groupId>com.birthdates</groupId>
            <artifactId>BungeePlayerTracker</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
</project>
```
This is an example class that would track two server's data (Factions, Skyblock)
```java
import com.birthdates.bungeeplayertracker.BungeePlayerTracker;
import com.birthdates.bungeeplayertracker.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class BungeePlayerTrackerTest extends JavaPlugin {

    private BungeePlayerTracker playerTracker;

    public void onEnable() {
        playerTracker = new BungeePlayerTracker();

        playerTracker.init(this, 3); //Update server data every 3 seconds
        playerTracker.startTracking("factions", "127.0.0.1:25566");
        playerTracker.startTracking("skyblock", "127.0.0.1:25567");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            ServerData skyblockData = playerTracker.getServerData("skyblock");
            Bukkit.getLogger().log(Level.WARNING, "Skyblock is currently " + (skyblockData.isOnline() ? "online with " + skyblockData.getPlayers() + " players" : "offline") + ".");
        }, 0L, 20L);
    }

}

```
