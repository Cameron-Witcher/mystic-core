package net.mysticcloud.spigot.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public class Heartbeat implements Runnable {
    @Override
    public void run() {
        //Do stuff

        for(Runnable palp : CoreUtils.getPalpitations()){
            Bukkit.getScheduler().runTaskLater(CoreUtils.getPlugin(), palp, 0);
        }
        Bukkit.getScheduler().runTaskLater(CoreUtils.getPlugin(), new Heartbeat(), 1);
    }
}
