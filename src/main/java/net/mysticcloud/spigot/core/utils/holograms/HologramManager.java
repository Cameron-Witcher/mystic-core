package net.mysticcloud.spigot.core.utils.holograms;

import net.mysticcloud.spigot.core.utils.misc.UID;
import org.bukkit.Location;
import org.bukkit.entity.Display;

import java.util.*;

public class HologramManager {

    private static final Map<UID, Hologram> holograms = new HashMap<>();
    private static final Map<UID, ClassicHologram> classicHolograms = new HashMap<>();

    public static Hologram createHologram(Location loc) {
        Hologram holo = new Hologram(new UID(), loc);
        holograms.put(holo.getUID(), holo);
        return holo;
    }

    public static Hologram getHologram(UID uid) {
        return holograms.get(uid);
    }

    public static ClassicHologram createClassicHologram(Location loc) {
        ClassicHologram holo = new ClassicHologram(new UID(), loc);
        classicHolograms.put(holo.getUID(), holo);
        return holo;
    }

    public static ClassicHologram getClassicHologram(UID uid) {
        return classicHolograms.get(uid);
    }
}
