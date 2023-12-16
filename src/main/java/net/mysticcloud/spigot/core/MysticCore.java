package net.mysticcloud.spigot.core;

import net.mysticcloud.spigot.core.commands.AdminCommands;
import net.mysticcloud.spigot.core.commands.NpcCommands;
import net.mysticcloud.spigot.core.commands.PlayerCommands;
import net.mysticcloud.spigot.core.listeners.*;
import net.mysticcloud.spigot.core.listeners.channels.MessageListener;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.accounts.AccountManager;
import net.mysticcloud.spigot.core.utils.npc.NpcManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class MysticCore extends JavaPlugin {

    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "mystic:mystic");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "mystic:bungee");
        getServer().getMessenger().registerIncomingPluginChannel(this, "mystic:mystic", new MessageListener());
        CoreUtils.init(this);

        new ChatListener(this);
        new InteractionListener(this);
        new ServerListener(this);
        new InventoryListener(this);
        new EntityListener(this);

        new NpcCommands(this,"npc");
        new AdminCommands(this, "update", "kick", "region", "sudo");
        new PlayerCommands(this, "inventory");


        //For reloads
        for(Player player : Bukkit.getOnlinePlayers()){
            AccountManager.getMysticPlayer(player.getUniqueId());
        }
    }

    public void onDisable() {
        NpcManager.disable();
    }

}
