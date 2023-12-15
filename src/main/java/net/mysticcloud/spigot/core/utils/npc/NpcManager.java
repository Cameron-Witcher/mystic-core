package net.mysticcloud.spigot.core.utils.npc;

import net.mysticcloud.spigot.core.utils.misc.UID;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class NpcManager {

    private static final Map<UID, Npc> npcs = new HashMap<>();

    public static Npc createNpc(Location location) {
        Npc npc = new Npc(location);
        npcs.put(npc.getUid(), npc);
        return npc;
    }

    public Npc getNpc(UID uid) {
        return npcs.getOrDefault(uid, null);
    }


}
