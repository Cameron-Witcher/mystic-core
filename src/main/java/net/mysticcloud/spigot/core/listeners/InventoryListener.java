package net.mysticcloud.spigot.core.listeners;


import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.gui.GuiItem;
import net.mysticcloud.spigot.core.utils.gui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.json2.JSONObject;

public class InventoryListener implements Listener {

    public InventoryListener(MysticCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        if (!e.getPlayer().hasMetadata("switchinv")) {
            GuiManager.closeGui((Player) e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Bukkit.broadcastMessage("Click happened.");
        if (GuiManager.getOpenGui(player) != null) {
            Bukkit.broadcastMessage("In GUI");
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == Material.AIR) return;
            if (!e.getCurrentItem().hasItemMeta()) return;
            Bukkit.broadcastMessage("Clicked an Item");
            if (GuiManager.getOpenGui(player).hasItem(e.getCurrentItem(), player)) {
                GuiItem item = GuiManager.getOpenGui(player).getItem(e.getCurrentItem(), player);
                if (item.hasAction()) item.processActions(player, e.getClick());
            }
        }
    }
}