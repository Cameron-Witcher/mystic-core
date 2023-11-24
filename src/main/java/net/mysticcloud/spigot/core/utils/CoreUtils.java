package net.mysticcloud.spigot.core.utils;

import net.mysticcloud.spigot.core.MysticCore;
import org.bukkit.Bukkit;

public class CoreUtils {

    private static MysticCore plugin = null;

    public static void init(MysticCore core){
        plugin = core;
    }

    public static MysticCore getPlugin(){
        return plugin;
    }

    public static void proxyKick(String p) {
        String u = "";
        String m = "";
        int a = 0;
        for (String b : p.split(" ")) {
            if (a == 0)
                u = b;
            else
                m = m == "" ? b : m + " " + b;
            a = a + 1;
        }
        if (Bukkit.getPlayer(u) == null)
            return;
        Bukkit.getPlayer(u).kickPlayer(m);
    }
}
