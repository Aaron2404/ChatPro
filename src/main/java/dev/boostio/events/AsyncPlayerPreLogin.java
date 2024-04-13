package dev.boostio.events;

import dev.boostio.ChatPro;
import dev.boostio.enums.ConfigOption;
import dev.boostio.managers.ColorManager;
import dev.boostio.managers.ConfigManager;
import dev.boostio.utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLogin implements Listener {
    private final ConfigManager configManager;
    private final ColorManager colorManager;

    public AsyncPlayerPreLogin(ChatPro plugin) {
        this.configManager = plugin.getConfigManager();
        this.colorManager = plugin.getColorManager();
    }

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        PlayerData playerData = new PlayerData();
        ChatColor defaultColor = this.colorManager.convertColor(configManager.getConfigurationOption(ConfigOption.DEFAULT_CHAT_COLOR));

        playerData.setChatColorName(defaultColor);
        ChatPro.getInstance().getPlayerData().put(event.getUniqueId(), playerData);
    }
}

