package dev.boostio;

import co.aikar.commands.PaperCommandManager;
import com.github.retrooper.packetevents.PacketEvents;
import dev.boostio.managers.ChatManager;
import dev.boostio.managers.ConfigManager;
import dev.boostio.managers.UpdateManager;
import dev.boostio.utils.PlayerData;
import dev.boostio.managers.StartupManager;
import io.github.retrooper.packetevents.bstats.Metrics;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

@Getter
public final class ChatPro extends JavaPlugin {
    public static String PREFIX = ChatColor.GRAY + "[" + ChatColor.AQUA + "ChatPro" + ChatColor.GRAY + "] ";
    private ConfigManager configManager;
    private ChatManager chatManager;

    private PaperCommandManager commandManager;
    private BukkitAudiences adventure;

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
        configManager = new ConfigManager(this);
        commandManager = new PaperCommandManager(this);
        chatManager = new ChatManager(this);
        adventure = BukkitAudiences.create(this);
        instance = this;

        new UpdateManager(this);
        new StartupManager(this);

        enableBStats();
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
        adventure.close();
        getLogger().info("Plugin has been uninitialized!");
    }

    private void enableBStats() {
        try {
            new Metrics(this, 21576);
        } catch (Exception e) {
            getLogger().warning("Something went wrong while enabling bStats.\n" + e.getMessage());
        }
    }
}
