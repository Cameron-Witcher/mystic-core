package net.mysticcloud.spigot.core.utils.regions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegionUtils {

    private static Map<UUID,Region> regions = new HashMap<>();

    public static Region getRegion(UUID uid){
        if(!regions.containsKey(uid)){
            regions.put(uid, new Region(0,0,0,0,0,0));
        }
        return regions.get(uid);
    }
}
