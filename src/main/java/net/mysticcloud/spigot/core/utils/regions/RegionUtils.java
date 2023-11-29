package net.mysticcloud.spigot.core.utils.regions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.json2.JSONArray;
import org.json2.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RegionUtils {

    private static Map<UUID, Region> regions = new HashMap<>();

    public static Region getRegion(UUID uid) {
        if (!regions.containsKey(uid)) {
            regions.put(uid, new Region(0, 0, 0, 0, 0, 0));
        }
        return regions.get(uid);
    }

    public static void pasteSave(Location loc, JSONArray save) {
        for (Object obj : save.toList()) {
            HashMap<String, Object> json = (HashMap<String, Object>) obj;
            loc.clone().add((Double) json.get("x"), (Double) json.get("y"), (Double) json.get("z")).getBlock().setBlockData(Bukkit.createBlockData(json.get("data") + ""));

        }
    }
}
