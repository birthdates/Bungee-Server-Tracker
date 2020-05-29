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
    <artifactId>JSONSystemTest</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <!-- REQUIRED !-->
    <repositories>
        <!-- Add the JSON System repo via GitHub !-->
        <repository>
            <id>jsonsystem-repo</id>
            <url>https://raw.githubusercontent.com/birthdates/BungeePlayerTracker/repository/</url>
        </repository>
    </repositories>

    <dependencies>
        <!-- Add the JSON System dependency and scope compile !-->
        <dependency>
            <groupId>com.birthdates</groupId>
            <artifactId>JSONSystem</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
</project>
```
This is an example class that would save/load a JSON file (test.json)
```java
import com.birthdates.jsonsystem.JSONFile;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TestFile extends JSONFile {

    public class Data {
        @SerializedName("Test")
        private String test;
        @GsonIgnore
        private String formattedTest;
        
        public Data(String test) {
            this.test = test;
            this.formattedTest = this.test.replace("test", "Test");
        }
    }

    public TestFile(Plugin plugin) {
        super(plugin, "test");
        getData(Data.class); 
    }
}
```
