package net.mysticcloud.spigot.core.listeners;

import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.npc.Npc;
import net.mysticcloud.spigot.core.utils.placeholder.PlaceholderUtils;
import net.mysticcloud.spigot.core.utils.regions.Region;
import net.mysticcloud.spigot.core.utils.regions.RegionUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.json2.JSONObject;

public class InteractionListener implements Listener {
    public InteractionListener(MysticCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().hasMetadata("npc")) {
            e.setCancelled(true);
            Npc npc = (Npc) e.getRightClicked().getMetadata("npc").get(0).value();
            npc.processActions(e.getPlayer());
            return;
        }
        if (e.getRightClicked().hasMetadata("command")) {
            //Examples JSON for command metadata
            //{"sender":"%player%","command","inv games"}
            //{"sender":"console","command":"give %player% emerald 4"}
            //{"command":"say Hello World!"}
            JSONObject command = (JSONObject) e.getRightClicked().getMetadata("command").get(0).value();
            String sender = command.has("sender") ? PlaceholderUtils.replace(e.getPlayer(), command.getString("sender")) : e.getPlayer().getName();
            Bukkit.dispatchCommand(sender.equalsIgnoreCase("console") ? Bukkit.getConsoleSender() : Bukkit.getPlayer(sender), PlaceholderUtils.replace(e.getPlayer(), command.getString("command")));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_AXE))) {
            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                Vector vec = new Vector(e.getClickedBlock().getX(), e.getClickedBlock().getY(), e.getClickedBlock().getZ());
                Region r = RegionUtils.getRegion(e.getPlayer().getUniqueId());
                if (r.setPos1(vec))
                    e.getPlayer().sendMessage(MessageUtils.colorize("&cPosition 1 set: (" + vec.getX() + ", " + vec.getY() + ", " + vec.getZ() + ") (" + r.getArea() + ")"));
                e.setCancelled(true);
            }
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Vector vec = new Vector(e.getClickedBlock().getX(), e.getClickedBlock().getY(), e.getClickedBlock().getZ());
                Region r = RegionUtils.getRegion(e.getPlayer().getUniqueId());
                if (r.setPos2(vec))
                    e.getPlayer().sendMessage(MessageUtils.colorize("&cPosition 2 set: (" + vec.getX() + ", " + vec.getY() + ", " + vec.getZ() + ") (" + r.getArea() + ")"));
                e.setCancelled(true);
            }

        }
    }
}
