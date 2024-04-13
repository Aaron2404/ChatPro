package dev.boostio.managers;

import dev.boostio.ChatPro;
import dev.boostio.enums.ConfigOption;

import java.util.EnumMap;
import java.util.Map;

public class ConfigManager {
    private final ChatPro plugin;
    private final Map<ConfigOption, Object> configurationOptions = new EnumMap<>(ConfigOption.class);

    public ConfigManager(ChatPro plugin) {
        this.plugin = plugin;

        saveDefaultConfiguration();
        loadConfigurationOptions();
    }

    private void saveDefaultConfiguration() {
        plugin.saveDefaultConfig();
    }

    private void loadConfigurationOptions() {
        for (ConfigOption option : ConfigOption.values()) {
            configurationOptions.put(option, plugin.getConfig().get(option.getKey(), option.getDefaultValue()));
        }
    }

    public <T> T getConfigurationOption(ConfigOption option) {
        return (T) configurationOptions.get(option);
    }
}