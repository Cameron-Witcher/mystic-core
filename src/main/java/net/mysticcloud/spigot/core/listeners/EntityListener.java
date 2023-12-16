package net.mysticcloud.spigot.core.listeners;

import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EntityListener implements Listener {
    public EntityListener(MysticCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("npc")) {
            e.setCancelled(true);
            return;
        }
    }
}