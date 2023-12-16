package net.mysticcloud.spigot.core.utils.npc;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.gui.GuiInventory;
import net.mysticcloud.spigot.core.utils.gui.GuiItem;
import net.mysticcloud.spigot.core.utils.misc.UID;
import net.mysticcloud.spigot.core.utils.placeholder.PlaceholderUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json2.JSONArray;
import org.json2.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NpcManager {

    private static final Map<UID, Npc> npcs = new HashMap<>();
    private static File npcFolder;

    public static void init() {
        npcFolder = new File(CoreUtils.getPlugin().getDataFolder() + "/npcs");
        registerPersistentNpcs();
    }

    private static void registerPersistentNpcs() {
        try {
            if (!npcFolder.exists()) npcFolder.mkdir();
            for (File file : npcFolder.listFiles())
                if (file.getName().toLowerCase().endsWith(".npc")) loadNpcFromFile(file);

        } catch (Exception e) {
            MessageUtils.log("There was an error registering guis.");
            e.printStackTrace();
        }
    }

    private static void loadNpcFromFile(File file) {
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);

        MessageUtils.log("Loading NPC file: " + file.getName());
        int x = 0;
        for (String npcName : fc.getConfigurationSection("npcs").getKeys(false)) {
            String key = "npcs." + npcName;
            Npc npc = createNpc(CoreUtils.decryptLocation(fc.getString(key + ".location")));
            if (fc.isSet(key + ".actions")) {
                for (String akey : fc.getConfigurationSection(key + ".actions").getKeys(false)) {
                    JSONObject action = new JSONObject();
                    action.put("id", akey);
                    for (String atype : fc.getConfigurationSection(key + ".actions." + akey).getKeys(false)) {
                        action.put(atype, fc.get(key + ".actions." + akey + "." + atype));
                    }
                    npc.addAction(action);
                }
            }
            if (fc.isSet(key + ".custom_name"))
                npc.setCustomName(MessageUtils.colorize(fc.getString(key + ".custom_name")));
            if (fc.isSet(key + ".custom_name_visible"))
                npc.setCustomNameVisible(fc.getBoolean(key + ".custom_name_visible"));
            npcs.put(npc.getUid(), npc);
            MessageUtils.log("Successfully loaded NPC: " + npc.getUid());
            x = x + 1;
        }
    }

    public static void disable() {
        for (Map.Entry<UID, Npc> entry : npcs.entrySet()) {
            entry.getValue().remove();
        }
        npcs.clear();
    }

    public static Npc createNpc(Location location) {
        Npc npc = new Npc(location);
        npcs.put(npc.getUid(), npc);
        return npc;
    }

    public static Npc getNpc(UID uid) {
        return npcs.getOrDefault(uid, null);
    }

    public static void removeNpc(UID uid) {
        if (getNpc(uid) != null) {
            Npc npc = getNpc(uid);
            npc.remove();
            npcs.remove(uid);
        }
    }

    public static Npc createPersistentNpc(Location location) {
        Npc npc = createNpc(location);
        try {
            File npcFile = new File(npcFolder.getPath() + "/command_created.npc");
            if (!npcFile.exists()) npcFile.createNewFile();
            FileConfiguration fc = YamlConfiguration.loadConfiguration(npcFile);
            fc.set("npcs." + npc.getUid() + ".location", CoreUtils.encryptLocation(location));
            fc.save(npcFile);
        } catch (IOException ex) {
            MessageUtils.log("Couldn't save NPC to file.");
        }
        return npc;
    }


}
