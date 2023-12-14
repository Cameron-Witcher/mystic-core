package net.mysticcloud.spigot.core.utils.holograms;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.misc.UID;
import net.mysticcloud.spigot.core.utils.placeholder.PlaceholderUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Display;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.LinkedList;

public class ClassicHologram {
    LinkedList<ArmorStand> lines = new LinkedList<>();

    Location loc;
    UID uid;

    protected ClassicHologram(UID uid, Location loc) {
        this.loc = loc;
        this.uid = uid;
    }

    public void setLine(int line, String message) {
        ArmorStand stand;
        if (lines.size() > line) stand = lines.get(line);
        else {
            stand = loc.getWorld().spawn(loc, ArmorStand.class);
            stand.setInvisible(true);
            stand.setGravity(false);
            stand.setInvulnerable(true);
            stand.setCustomNameVisible(true);
        }
        if (stand.hasMetadata("info")) stand.removeMetadata("info", CoreUtils.getPlugin());
        stand.setMetadata("info", new FixedMetadataValue(CoreUtils.getPlugin(), message));
        stand.setCustomName(MessageUtils.colorize(PlaceholderUtils.replace(null, message)));

        update();
    }

    public UID getUID() {
        return uid;
    }

    public String getLine(int i) {
        return lines.get(i).getCustomName();
    }

    public void update() {

        for (int i = 0; i != lines.size(); i++) {
            ArmorStand disp = lines.get(i);
            disp.teleport(loc.clone().add(0, -0.26 * i, 0));

        }
    }
}
