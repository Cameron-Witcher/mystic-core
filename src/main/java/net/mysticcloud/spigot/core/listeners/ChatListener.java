package net.mysticcloud.spigot.core.listeners;

import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    public ChatListener(MysticCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        e.setFormat(MessageUtils.colorize("&7" + e.getPlayer().getName() + ":" + "&f" + e.getMessage()));
    }
}
