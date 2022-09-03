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

import java.util.HashMap;
import java.util.UUID;

@Getter
public final class ChatPro extends JavaPlugin {

    public static boolean colorCodes = false;
    public static String colorCommandNoPermission = "";
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
            colorCommandNoPermission = getConfig().getString("colorCommandNoPermission");
        } catch (Exception e) {
            Bukkit.getLogger().warning("Something went wrong while getting the settings from the config file");
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
