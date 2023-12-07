package net.mysticcloud.spigot.core.utils;

import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.utils.regions.RegionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class CoreUtils {

    private static MysticCore plugin = null;
    private static List<Runnable> palpitations = new ArrayList<>();

    public static void init(MysticCore core) {
        plugin = core;
        Bukkit.getScheduler().runTaskLater(plugin, new Heartbeat(), 1);
        plugin.getConfig();
        RegionUtils.init();
    }

    public static void addPalpitation(Runnable runnable) {
        palpitations.add(runnable);
    }

    public static List<Runnable> getPalpitations() {
        return palpitations;
    }

    public static MysticCore getPlugin() {
        return plugin;
    }


    public static void proxyKick(String p) {
        String u = "";
        String m = "";
        int a = 0;
        for (String b : p.split(" ")) {
            if (a == 0) u = b;
            else m = m == "" ? b : m + " " + b;
            a = a + 1;
        }
        if (Bukkit.getPlayer(u) == null) return;
        Bukkit.getPlayer(u).kickPlayer(m);
    }

    public static Double distance(Location loc1, Location loc2) {
        return Math.sqrt(Math.pow(loc2.getX() - loc1.getX(), 2) + Math.pow(loc2.getY() - loc1.getY(), 2) + Math.pow(loc2.getZ() - loc1.getZ(), 2));
    }

    private boolean deleteWorld(File path) {
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
        return (path.delete());
    }

    private void copyWorld(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
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


    public static boolean downloadFile(String url, String filename, String... auth) {

        boolean success = true;
        InputStream in = null;
        FileOutputStream out = null;

        try {

            URL myUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setDoOutput(true);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestMethod("GET");

            if (auth != null && auth.length >= 2) {
                String userCredentials = auth[0].trim() + ":" + auth[1].trim();
                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
                conn.setRequestProperty("Authorization", basicAuth);
            }
            in = conn.getInputStream();
            out = new FileOutputStream(filename);
            int c;
            byte[] b = new byte[1024];
            while ((c = in.read(b)) != -1) out.write(b, 0, c);

        } catch (Exception ex) {
            MessageUtils.log(("There was an error downloading " + filename + ". Check console for details."));
            ex.printStackTrace();
            success = false;
        } finally {
            if (in != null) try {
                in.close();
            } catch (IOException e) {
                MessageUtils.log(("There was an error downloading " + filename + ". Check console for details."));
                e.printStackTrace();
            }
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                MessageUtils.log(("There was an error downloading " + filename + ". Check console for details."));
                e.printStackTrace();
            }
        }
        return success;
    }
}
