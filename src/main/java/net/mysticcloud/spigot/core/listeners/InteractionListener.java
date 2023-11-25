package net.mysticcloud.spigot.core.listeners;

import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.regions.Region;
import net.mysticcloud.spigot.core.utils.regions.RegionUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class InteractionListener implements Listener {
    public InteractionListener(MysticCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_AXE))) {
            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                Vector vec = new Vector(e.getClickedBlock().getX(), e.getClickedBlock().getY(), e.getClickedBlock().getZ());
                e.getPlayer().sendMessage(MessageUtils.colorize("&cPosition 1 set: (" + vec.getX() + ", " + vec.getY() + ", " + vec.getZ() + ")"));
                RegionUtils.getRegion(e.getPlayer().getUniqueId()).setPos1(vec);
            }
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                Vector vec = new Vector(e.getClickedBlock().getX(), e.getClickedBlock().getY(), e.getClickedBlock().getZ());
                e.getPlayer().sendMessage(MessageUtils.colorize("&cPosition 1 set: (" + vec.getX() + ", " + vec.getY() + ", " + vec.getZ() + ")"));
                RegionUtils.getRegion(e.getPlayer().getUniqueId()).setPos2(vec);
            }

        }
    }
}
