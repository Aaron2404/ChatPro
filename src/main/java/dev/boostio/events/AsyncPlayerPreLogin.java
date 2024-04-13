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

import java.util.UUID;

public class AsyncPlayerPreLogin implements Listener {
    private final ConfigManager configManager;
    private final ColorManager colorManager;

    public AsyncPlayerPreLogin(ChatPro plugin) {
        this.configManager = plugin.getConfigManager();
        this.colorManager = plugin.getColorManager();
    }

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();

        PlayerData playerData = new PlayerData();
        ChatPro.getInstance().getPlayerData().put(uuid, playerData);

        ChatColor defaultColor = this.colorManager.convertColor(configManager.getConfigurationOption(ConfigOption.DEFAULT_CHAT_COLOR));

        //Set default chat color.
        ChatPro.getInstance().getPlayerData().get(uuid).setChatColorName(defaultColor);
    }
}

