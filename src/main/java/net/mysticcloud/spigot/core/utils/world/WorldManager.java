package net.mysticcloud.spigot.core.utils.world;

import net.mysticcloud.spigot.core.utils.misc.EmptyChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WorldManager {


    public static World createWorld(String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.generator(new EmptyChunkGenerator());
        World world = wc.createWorld();
        assert world != null;
        world.getBlockAt(0, 0, 0).setType(Material.BEDROCK);
        world.setSpawnLocation(0, 1, 0);
        return world;
    }


    public static void unloadWorld(World world) {
        Bukkit.unloadWorld(world, true);
    }

    public static void deleteWorld(World world) {
        Bukkit.unloadWorld(world, false);
        File path = world.getWorldFolder();
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }

        path.delete();

    }

    private static boolean deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }

        return path.delete();
    }

    public static void copyWorld(World source, String target) {
        copyWorld(source.getWorldFolder(), new File(source.getWorldFolder().getParentFile() + "/" + target));
    }

    private static void copyWorld(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if (source.isDirectory()) for (File file : source.listFiles())
                if (file.getName().endsWith(".arena")) ignore.add(file.getName());
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists()) target.mkdirs();
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {

        }
    }
}
