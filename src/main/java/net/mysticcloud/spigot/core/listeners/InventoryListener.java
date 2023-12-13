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
import org.bukkit.plugin.java.JavaPlugin;
import org.json2.JSONObject;

public class InventoryListener implements Listener {

    public InventoryListener(MysticCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        if (!e.getPlayer().hasMetadata("switchinv")) {
            GuiManager.closeInventory((Player) e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerInventory(InventoryClickEvent e) {

        if (((GuiManager.getGuis().containsKey(GuiManager.getOpenInventory((Player) e.getWhoClicked()))))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == null) return;
            if (!e.getCurrentItem().hasItemMeta()) return;

            if (GuiManager.getGuis().get(GuiManager.getOpenInventory((Player) e.getWhoClicked())).hasItem(e.getCurrentItem(), (Player) e.getWhoClicked())) {
                GuiItem item = GuiManager.getGuis().get(GuiManager.getOpenInventory((Player) e.getWhoClicked())).getItem(e.getCurrentItem(), (Player) e.getWhoClicked());
                if (item.hasAction()) {
                    if (item.isSingleAction()) {
                        item.processAction((Player) e.getWhoClicked());
                    } else {
                        for (int i = 0; i < item.getActions().length(); i++) {
                            JSONObject action = item.getActions().getJSONObject(i);
                            if (e.getClick().equals(ClickType.valueOf(action.getString("click").toUpperCase().replaceAll("_CLICK", "")))) {
                                if (!item.processAction((Player) e.getWhoClicked(), action)) {
                                    MessageUtils.log("Could not process action. Stopping.");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}