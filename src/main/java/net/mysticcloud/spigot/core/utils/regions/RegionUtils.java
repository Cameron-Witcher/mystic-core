package net.mysticcloud.spigot.core.utils.regions;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Structure;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.json2.JSONArray;
import org.json2.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class RegionUtils {

    private static final File regionDir = new File(CoreUtils.getPlugin().getDataFolder() + "/regions");

    private static Map<UUID, Region> regions = new HashMap<>();

    public static void init() {
        MessageUtils.prefixes("region", "&3&lRegions &7> &f");
        if (!regionDir.exists()) {
            MessageUtils.log("Region file creation: " + (regionDir.mkdirs() ? "success" : "FAILED."));
        }
    }

    public static Region getRegion(UUID uid) {
        if (!regions.containsKey(uid)) {
            regions.put(uid, new Region(0, 0, 0, 0, 0, 0));
        }
        return regions.get(uid);
    }

    public static void pasteSave(String name, Location loc) {

        File file = new File(regionDir.getPath() + "/" + name + ".region");
        if (!file.exists()) {
            MessageUtils.log(MessageUtils.prefixes("Save doesn't exist"));
            return;
        }
        JSONArray save;
        try {
            Scanner reader = new Scanner(file);
            save = new JSONArray(reader.nextLine());
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new RuntimeException(e);
        }


        for (int i = 0; i < save.length(); i++) {
            JSONObject data = save.getJSONObject(i);
            loc.clone().add(data.getInt("x"), data.getInt("y"), data.getInt("z")).getBlock().setBlockData(Bukkit.createBlockData(data.getString("data")));
        }
    }

    public static JSONArray getSave(String name) {
        File file = new File(regionDir.getPath() + "/" + name + ".region");
        if (!file.exists()) {
            MessageUtils.log(MessageUtils.prefixes("Save doesn't exist"));
        } else {
            JSONArray save;
            try {
                Scanner reader = new Scanner(file);
                save = new JSONArray(reader.nextLine());
                reader.close();
            } catch (FileNotFoundException var6) {
                System.out.println("An error occurred.");
                throw new RuntimeException(var6);
            }

            return save;

        }
        return null;
    }

    public static void saveRegion(String name, Player player) {

        File file = new File(regionDir.getPath() + "/" + name + ".region");
        if (file.exists()) {
            file.delete();
            try {
                MessageUtils.log("Region file creation: " + (file.createNewFile() ? "success" : "FAILED."));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        JSONArray array = new JSONArray();

        for (Map.Entry<Vector, Block> e : getRegion(player.getUniqueId()).getBlocks(player).entrySet()) {
            String extra = "";
            if (e.getValue().getType().equals(Material.STRUCTURE_BLOCK)) {
                Structure s = (Structure) e.getValue().getState();
                extra = ",\"structure_data\":{\"structure\":\"" + s.getStructureName() + "\"}";
            }
            array.put(new JSONObject("{\"x\":" + e.getKey().getX() + ",\"y\":" + e.getKey().getY() + ",\"z\":" + e.getKey().getZ() + ",\"data\":\"" + e.getValue().getBlockData().getAsString(false) + "\"" + extra + "}"));
        }
        File rf = new File(CoreUtils.getPlugin().getDataFolder() + "/regions.yml");
        if (!rf.exists()) {
            try {
                MessageUtils.log("Region file creation: " + (rf.createNewFile() ? "success" : "FAILED."));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(array.toString());
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            throw new RuntimeException(e);

        }

    }

    public static boolean saveExists(String name) {
        return new File(regionDir.getPath() + "/" + name + ".region").exists();
    }
}
