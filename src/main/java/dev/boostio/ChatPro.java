package dev.boostio;

import co.aikar.commands.PaperCommandManager;
import com.github.retrooper.packetevents.PacketEvents;
import dev.boostio.Utils.PlayerData;
import dev.boostio.Utils.UpdateChecker;
import dev.boostio.managers.StartupManager;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
public final class ChatPro extends JavaPlugin {

    public static String PREFIX = ChatColor.GRAY + "[" + ChatColor.AQUA + "ChatPro" + ChatColor.GRAY + "] ";

    public static String colorCommandNoPermission = "";
    public static String blockedMessageNotification = "";
    public static String filteredWordReplacement = "";
    public static String defaultChatColor = "";
    public static boolean colorCodes = false;
    public static boolean blockMessage = false;
    public static boolean replaceWordInMessage = false;
    public static boolean filterIPs = false;
    public static boolean betterMessageFormat = false;
    private PaperCommandManager commandManager;
    private BukkitAudiences adventure;


    public static List<String> filteredWords;

    @Getter
    private static ChatPro instance;
    private final HashMap<UUID, PlayerData> playerData = new HashMap<>();

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(false)
                .bStats(true);

        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        commandManager = new PaperCommandManager(this);
        adventure = BukkitAudiences.create(this);
        instance = this;

        saveDefaultConfig();

        try {
            colorCodes = getConfig().getBoolean("colorCodes");
            blockMessage = getConfig().getBoolean("blockMessage");
            replaceWordInMessage = getConfig().getBoolean("replaceWordInMessage");
            filterIPs = getConfig().getBoolean("filterIPs");
            betterMessageFormat = getConfig().getBoolean("betterMessageFormat");
            colorCommandNoPermission = getConfig().getString("colorCommandNoPermission");
            filteredWordReplacement = getConfig().getString("filteredWordReplacement");
            blockedMessageNotification = getConfig().getString("blockedMessageNotification");
            defaultChatColor = getConfig().getString("defaultChatColor");
            filteredWords = getConfig().getStringList("filteredWords");
        } catch (Exception e) {
            Bukkit.getLogger().warning("Something went wrong while getting the settings from the config file");
        }

        if (replaceWordInMessage && blockMessage) {
            Bukkit.getConsoleSender().sendMessage(PREFIX + ChatColor.RED + "You cannot have both replaceWordInMessage and blockMessage enabled, this will result in the messages just being blocked if you want them to be replaced: \n turn off blockMessage in the config.yml");
            replaceWordInMessage = false;
        }

        new StartupManager(this);
        UpdateChecker.checkForUpdate();

        Bukkit.getConsoleSender().sendMessage(PREFIX + ChatColor.GREEN + "Started ChatPro successfully!");
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
        adventure.close();
        getLogger().info("Plugin has been uninitialized!");
    }
}
