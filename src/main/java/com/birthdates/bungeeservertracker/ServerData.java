package com.birthdates.bungeeservertracker;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.logging.Level;

@Getter
public class ServerData {
    @Setter
    private int players;
    @Setter
    private boolean online;
    private String IP;
    private int port;

    public ServerData(String IP) {
        if(IP == null) {
            return;
        }
        try {
            port = Integer.parseInt(IP.split(":")[1]);
        } catch (Exception ignored) {
            Bukkit.getLogger().log(Level.SEVERE, "Invalid IP provided for server (requires port): " + IP);
        }
        this.IP = IP;
    }
}
