package com.birthdates.bungeeservertracker;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.bukkit.Bukkit.getServer;

public class BungeeServerTracker {

    private Plugin plugin;

    private int taskID;

    private final String channel = "BungeePlayerTracker";
    private Map<String, ServerData> serverData;

    public void init(Plugin plugin, long updateInterval) {
        this.plugin = plugin;
        serverData = new HashMap<>();

        getServer().getMessenger().registerOutgoingPluginChannel(plugin, channel);
        getServer().getMessenger().registerIncomingPluginChannel(plugin, channel, this::onPluginMessageReceived);

        startUpdating(updateInterval);
    }

    public void destroy() {
        getServer().getMessenger().unregisterIncomingPluginChannel(plugin, channel);
        getServer().getMessenger().unregisterOutgoingPluginChannel(plugin, channel);

        Bukkit.getScheduler().cancelTask(taskID);
    }

    public void startTracking(String server, String IP) {
        Validate.isTrue(!serverData.containsKey(server));

        serverData.put(server, new ServerData(IP));
    }

    public boolean endTracking(String server) {
        return serverData.remove(server) != null;
    }

    public ServerData getServerData(String server) {
        if(!serverData.containsKey(server)) {
            return null;
        }

        return serverData.get(server);
    }

    public int getGlobalPlayers() {
        return serverData.values().stream().mapToInt(ServerData::getPlayers).sum();
    }

    private void startUpdating(long updateInterval) {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            Optional<? extends Player> potentialPlayer =  Bukkit.getOnlinePlayers().stream().findAny();
            if(!potentialPlayer.isPresent()) {
                return;
            }
            Player player = potentialPlayer.get();

            for (String server : serverData.keySet()) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("PlayerCount");
                out.writeUTF(server);

                player.sendPluginMessage(plugin, channel, out.toByteArray());
            }
        }, 0L, 20L * updateInterval);
    }

    private void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if(!this.channel.equals(channel)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(data);
        String subChannel = in.readUTF();

        if(!subChannel.equals("PlayerCount")) {
            return;
        }

        String server = in.readUTF();
        ServerData serversData = serverData.getOrDefault(server, null);

        if(serversData == null) {
            return;
        }

        serversData.setPlayers(in.readInt());
        if(serversData.getIP() == null) {
            return;
        }
        serversData.setOnline(isServerOnline(serversData.getIP(), serversData.getPort()));
    }

    private boolean isServerOnline(String IP, int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(IP, port), 15);
            socket.close();
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }
}
