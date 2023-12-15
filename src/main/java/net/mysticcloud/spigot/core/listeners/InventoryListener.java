package net.mysticcloud.spigot.core.listeners;


import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.gui.GuiItem;
import net.mysticcloud.spigot.core.utils.gui.GuiManager;
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

        if (GuiManager.getOpenGui((Player) e.getWhoClicked()) != null && ((GuiManager.getGuis().containsKey(GuiManager.getOpenGui((Player) e.getWhoClicked()).getId())))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == null) return;
            if (!e.getCurrentItem().hasItemMeta()) return;

            if (GuiManager.getGuis().get(GuiManager.getOpenGui((Player) e.getWhoClicked()).getId()).hasItem(e.getCurrentItem(), (Player) e.getWhoClicked())) {
                GuiItem item = GuiManager.getGuis().get(GuiManager.getOpenGui((Player) e.getWhoClicked()).getId()).getItem(e.getCurrentItem(), (Player) e.getWhoClicked());
                if (item.hasAction()) item.processActions((Player) e.getWhoClicked(), e.getClick());
            }
        }
    }
}