package net.mysticcloud.spigot.core;

import net.mysticcloud.spigot.core.listeners.channels.MessageListener;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;



public class MysticCore extends JavaPlugin {

    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "mystic:mystic");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "mystic:bungee");
        getServer().getMessenger().registerIncomingPluginChannel(this, "mystic:mystic", new MessageListener());
        CoreUtils.init(this);
    }

    public void onDisable() {

    }

}
