package net.mysticcloud.spigot.core.utils.regions;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

    public static void pasteSave(String name, Player player) {

        File file = new File(regionDir.getPath() + "/" + name + ".region");
        if (!file.exists()) {
            player.sendMessage(MessageUtils.prefixes("region") + "Sorry that region doesn't exist. Try again, or try /region list-saves to see all saved regions.");
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
            player.getLocation().clone().add(data.getInt("x"), data.getInt("y"), data.getInt("z")).getBlock().setBlockData(Bukkit.createBlockData(data.getString("data")));
        }
//        for (Object obj : save.toList()) {
//            HashMap<String, Object> json = (HashMap<String, Object>) obj;
//            loc.clone().add(((BigDecimal) json.get("x")).intValue(), ((BigDecimal) json.get("y")).intValue(), ((BigDecimal) json.get("z")).intValue())
//
//        }
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

        for (Map.Entry<Vector, BlockData> e : getRegion(player.getUniqueId()).getBlocks(player).entrySet())
            array.put(new JSONObject("{\"x\":" + e.getKey().getX() + ",\"y\":" + e.getKey().getY() + ",\"z\":" + e.getKey().getZ() + ",\"data\":\"" + e.getValue().getAsString(false) + "\"}"));
        File rf = new File(CoreUtils.getPlugin().getDataFolder() + "/regions.yml");
        if (!rf.exists()) {
            try {
                MessageUtils.log("Region file creation: " + (rf.createNewFile() ? "success" : "FAILED."));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(array.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            throw new RuntimeException(e);

        }

    }

    public static void getSave(String name) {

    }
}
