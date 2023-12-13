package net.mysticcloud.spigot.core.utils.gui;

import java.util.ArrayList;
import java.util.List;

import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.placeholder.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json2.JSONArray;
import org.json2.JSONObject;


public class GuiItem {
    String id;
    String display_name = "default_name";
    Material mat = Material.GRASS_BLOCK;
    List<String> lore = null;
    boolean single_action = false;
    double buy = 0;
    double sell = 0;
    JSONObject action = new JSONObject();
    JSONArray actions = new JSONArray();
    boolean does_action = false;
    ItemStack storedItem = null;

    public GuiItem(String id) {
        this.id = id;
    }

    public GuiItem setDisplayName(String display_name) {
        this.display_name = display_name;
        return this;
    }

    public GuiItem setMaterial(Material mat) {
        this.mat = mat;
        return this;
    }

    public GuiItem setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public GuiItem setSingleAction(boolean single_action) {
        this.single_action = single_action;
        return this;
    }

    public boolean isSingleAction() {
        return single_action;
    }

    public GuiItem setBuyPrice(String string) {
        try {
            buy = Double.parseDouble(string);
        } catch (NumberFormatException ex) {
        }
        return this;
    }

    public double getBuyPrice() {
        return buy;
    }

    public GuiItem setSellPrice(String string) {
        try {
            sell = Double.parseDouble(string);
        } catch (NumberFormatException ex) {
        }
        return this;
    }

    public double getSellPrice() {
        return sell;
    }

    public GuiItem setSingleAction(JSONObject json) {
        does_action = true;
        this.action = json;
        return this;
    }

    public GuiItem setActions(JSONArray actions) {
        does_action = true;
        this.actions = actions;
        return this;
    }

    public JSONObject getAction() {
        return action;
    }

    public JSONArray getActions() {
        return actions;
    }

    public String getIdentifier() {
        return id;
    }

    public ItemStack getItem(Player player) {
        if (storedItem == null) {
            ItemStack item = new ItemStack(mat);
            ItemMeta meta = item.getItemMeta();
            if (lore != null) {
                List<String> tmp = new ArrayList<>();
                if (meta.hasLore()) for (String a : meta.getLore())
                    tmp.add(a);
                for (String a : lore) {
                    tmp.add(MessageUtils.colorize(PlaceholderUtils.replace(player, a)));
                }
                meta.setLore(tmp);
            }
            if (meta != null) {

                meta.addItemFlags(ItemFlag.values());
                meta.setDisplayName(MessageUtils.colorize(PlaceholderUtils.replace(player, display_name)));
                item.setItemMeta(meta);
            }
            this.storedItem = item;
        }
        return this.storedItem.clone();
    }

    public boolean hasAction() {
        return does_action;
    }

    public boolean processAction(Player player) {
        return processAction(player, action);
    }

    public boolean processAction(Player player, JSONObject action) {
        switch (action.getString("action").toLowerCase()) {
            case "sound":
                player.playSound(player.getLocation(), Sound.valueOf(action.getString("sound")), 10F, 1F);
                return true;
//            case "sell":
//                ItemStack t = getItem(player);
//                if (action.has("amount"))
//                    t.setAmount(Integer.parseInt(action.getString("amount")));
//                if (player.getInventory().contains(t)) {
//                    player.getInventory().remove(t);
//                    Utils.getEconomy().depositPlayer(player, item.getSellPrice());
//                    return true;
//                } else
//                    return false;
//            case "buy":
//                int amount = action.has("amount") ? Integer.parseInt(action.getString("amount")) : 1;
//                double price = getBuyPrice() * amount;
//                if (Utils.getEconomy().has(player, price)) {
//                    Utils.getEconomy().withdrawPlayer(player, price);
//                    if (action.has("item")) {
//                        ItemStack i = decodeItem(Utils.setPlaceholders(player, action.getString("item")));
//                        i.setAmount(amount);
//                        player.getInventory().addItem(i);
//                        return true;
//                    }
//                    if (action.has("command")) {
//                        String sender = action.has("sender") ? action.getString("sender") : "player";
//                        String cmd = Utils.setPlaceholders(player, action.getString("command"));
//                        Bukkit.dispatchCommand(sender.equalsIgnoreCase("CONSOLE") ? Bukkit.getConsoleSender() : player,
//                                cmd);
//                    }
//                    return true;
//                } else
//                    return false;
            case "send_message":
                player.sendMessage(MessageUtils.colorize(action.getString("message")));
                return true;
            case "open_gui":
                try {
                    GuiManager.openGui(player, GuiManager.getGui(action.getString("gui")));
                } catch (NullPointerException ex) {
                    player.sendMessage(MessageUtils.prefixes("gui") + "There was an error opening that GUI. Does it exist?");
                }
                return true;
            case "join_server":
                MessageUtils.sendPluginMessage(player, "BungeeCord", "Connect", action.getString("server"));
                return true;

            case "command":
                String sender = action.has("sender") ? action.getString("sender") : "player";
                String cmd = PlaceholderUtils.replace(player, action.getString("command"));
                Bukkit.dispatchCommand(sender.equalsIgnoreCase("CONSOLE") ? Bukkit.getConsoleSender() : player, cmd);
                return true;
            case "close_gui":
                player.closeInventory();
                return true;
        }
        if (action.has("error_message"))
            player.sendMessage(PlaceholderUtils.replace(player, action.getString("error_message")));


        return false;

    }
}
