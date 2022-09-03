package dev.boostio;

import dev.boostio.Commands.NameColor;
import dev.boostio.Events.AsyncPlayerChat;
import dev.boostio.Events.AsyncPlayerPreLogin;
import dev.boostio.Events.InventoryClick;
import dev.boostio.Events.PlayerQuit;
import dev.boostio.Utils.PlayerData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
public final class ChatPro extends JavaPlugin {

    public static boolean colorCodes = false;
    public static String colorCommandNoPermission = "";
    public static boolean blockMessage = false;
    public static boolean replaceWordInMessage = false;
    public static boolean filterIPs = false;
    public static String filteredWordReplacement = "";

    public static List<String> filteredWords;

    @Getter
    private static ChatPro instance;
    private final HashMap<UUID, PlayerData> playerData = new HashMap<>();
    String version = getDescription().getVersion();


    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        try {
            colorCodes = getConfig().getBoolean("colorCodes");
            blockMessage = getConfig().getBoolean("blockMessage");
            replaceWordInMessage = getConfig().getBoolean("replaceWordInMessage");
            filterIPs = getConfig().getBoolean("filterIPs");
            colorCommandNoPermission = getConfig().getString("colorCommandNoPermission");
            filteredWordReplacement = getConfig().getString("filteredWordReplacement");
            filteredWords = getConfig().getStringList("filteredWords");
        } catch (Exception e) {
            Bukkit.getLogger().warning("Something went wrong while getting the settings from the config file");
        }

        if(replaceWordInMessage && blockMessage){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "You cannot have both replaceWordInMessage and blockMessage enabled, this will result in the messages just being blocked if you want them to be replaced: \n turn off blockMessage in the config.yml");
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Started ChatPro version " + ChatColor.RED + version);

        //Events
        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLogin(), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);

        //Commands
        getCommand("color").setExecutor(new NameColor());
    }

    @Override
    public void onDisable() {
    }
}
