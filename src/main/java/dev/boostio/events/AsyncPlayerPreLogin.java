package dev.boostio.events;

import dev.boostio.ChatPro;
import dev.boostio.enums.ConfigOption;
import dev.boostio.managers.ConfigManager;
import dev.boostio.utils.ColoringUtils;
import dev.boostio.utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class AsyncPlayerPreLogin implements Listener {
    private final ChatPro plugin;
    private final ConfigManager configManager;

    public AsyncPlayerPreLogin(ChatPro plugin){
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();

        PlayerData playerData = new PlayerData();
        ChatPro.getInstance().getPlayerData().put(uuid, playerData);

        ChatColor defaultColor = ColoringUtils.convertColor(configManager.getConfigurationOption(ConfigOption.DEFAULT_CHAT_COLOR));

        //Set default chat color.
        ChatPro.getInstance().getPlayerData().get(uuid).setChatColorName(defaultColor);
    }
}
