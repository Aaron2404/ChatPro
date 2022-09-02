package dev.boostio;

import dev.boostio.Commands.NameColor;
import dev.boostio.Utils.PlayerData;
import dev.boostio.event.ChooseColor;
import dev.boostio.event.OnPlayerChat;
import dev.boostio.event.PreLogin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

@Getter
public final class ChatPro extends JavaPlugin {


    private static ChatPro instance;
    private final HashMap<UUID, PlayerData> playerData = new HashMap<>();
    public static boolean colorCodes = false;
    public static String colorCommandNoPermission = "";
    String version = getDescription().getVersion();

    public static ChatPro getInstance() {return instance;}

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        try{
            colorCodes = getConfig().getBoolean("colorCodes");
            colorCommandNoPermission = getConfig().getString("colorCommandNoPermission");
        }catch(Exception e){
            Bukkit.getLogger().warning("Something went wrong while getting the settings from the config file");
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Started ChatPro version " + ChatColor.RED + version);

        //Events
        getServer().getPluginManager().registerEvents(new OnPlayerChat(), this);
        getServer().getPluginManager().registerEvents(new PreLogin(), this);
        getServer().getPluginManager().registerEvents(new ChooseColor(), this);

        //Commands
        getCommand("color").setExecutor(new NameColor());
    }

    @Override
    public void onDisable() {
    }
}
