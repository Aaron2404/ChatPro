package dev.boostio;

import dev.boostio.event.OnPlayerChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatPro extends JavaPlugin {

    public static boolean colorCodes = false;
    String version = getDescription().getVersion();

    @Override
    public void onEnable() {

        saveDefaultConfig();

        try{
            colorCodes = getConfig().getBoolean("colorCodes");
        }catch(Exception e){
            Bukkit.getLogger().warning("Something went wrong while getting the settings from the config file");
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Started ChatPro version " + ChatColor.RED + version);

        //Events
        getServer().getPluginManager().registerEvents(new OnPlayerChat(), this);
    }

    @Override
    public void onDisable() {
    }
}
