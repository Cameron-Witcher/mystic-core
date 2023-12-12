package net.mysticcloud.spigot.core.utils.accounts;

import net.mysticcloud.spigot.core.utils.sql.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json2.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountManager {

    private static Map<UUID, MysticPlayer> mysticPlayers = new HashMap<>();

    public static MysticPlayer getMysticPlayer(UUID uid) {
        if (mysticPlayers.containsKey(uid)) return mysticPlayers.get(uid);
        MysticPlayer mysticPlayer;
        try {
            ResultSet rs = SQLUtils.getDatabase("mysticcloud").query("SELECT * FROM players WHERE uuid='" + uid + "';");
            if (rs != null) while (rs.next()) mysticPlayer = new MysticPlayer(new JSONObject(rs.getString("data")));
        } catch (SQLException ignored) {
        }
        mysticPlayer = new MysticPlayer();
        mysticPlayers.put(uid, mysticPlayer);
        return mysticPlayer;

    }

    public static void saveMysticPlayer(Player player) {
        MysticPlayer mp = getMysticPlayer(player.getUniqueId());
        mp.putData("last_seen", new Date().getTime());
        if(SQLUtils.getDatabase("mysticcloud").update("UPDATE players SET username=\"" + player.getName() + "\",ip=\"" + player.getAddress().getAddress().getHostAddress() + "\",data=\"" + SQLUtils.escape(mp.getData().toString()) + "\" WHERE uuid=\"" + player.getUniqueId() + "\";") < 1){
            SQLUtils.getDatabase("mysticcloud").input("INSERT INTO players (uuid,username,ip,data) VALUES (\"" + player.getUniqueId() + "\",\"" + player.getName() + "\",\"" + player.getAddress().getAddress().getHostAddress() + "\",\"" + SQLUtils.escape(mp.getData().toString()) + "\");");
        }
    }
}
