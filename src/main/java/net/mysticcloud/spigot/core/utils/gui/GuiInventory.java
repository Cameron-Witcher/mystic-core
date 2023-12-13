package net.mysticcloud.spigot.core.utils.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.mysticcloud.spigot.core.utils.placeholder.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiInventory {

    String id;
    String display_name = "";
    String config = "xxxxxx";
    int size = 9;
    Map<String, GuiItem> items = new HashMap<>();

    @Deprecated
    public GuiInventory(String id) {
        this.id = id;
    }

    @Deprecated
    public GuiInventory(String id, String display_name) {
        this(id);
        this.display_name = display_name;
    }

    @Deprecated
    public GuiInventory(String id, String display_name, int size) {
        this(id, display_name);
        this.size = size;
    }

    public GuiInventory(String id, String display_name, int size, String config) {
        this(id, display_name, size);
        this.config = config;
    }

    public String getId() {
        return id;
    }

    public void addItem(String identifier, GuiItem item) {
        items.put(identifier, item);
    }

    public Inventory getInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, size, PlaceholderUtils.replace(player, display_name));
        for (int i = 0; i != config.length(); i++) {
            String key = config.substring(i, i + 1);
            inv.setItem(i, getGuiItem(key).getItem(player));
        }
        return inv;
    }

    public GuiItem getGuiItem(String key) {
        return items.get(key);
    }

    public String getKey(ItemStack item, Player player) {
        for (Entry<String, GuiItem> e : items.entrySet()) {
            if (e.getValue().getItem(player).equals(item)) return e.getKey();
        }
        return "";
    }

    public boolean hasItem(ItemStack item, Player player) {
        for (Entry<String, GuiItem> e : items.entrySet()) {
            if (e.getValue().getItem(player).equals(item)) return true;
        }
        return false;
    }

    public GuiItem getItem(ItemStack item, Player player) {
        for (Entry<String, GuiItem> e : items.entrySet()) {
            if (e.getValue().getItem(player).equals(item)) return e.getValue();
        }
        return null;
    }

    public String getConfig() {
        return config;
    }

    public Map<String, GuiItem> getItemMap() {
        return items;
    }

}