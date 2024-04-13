/*
 *
 *  * This file is part of ChatPro - https://github.com/Aaron2404/ChatPro
 *  * Copyright (C) 2022 Aaron and contributors
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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

    public boolean getBoolean(ConfigOption option) {
        return (boolean) configurationOptions.get(option);
    }
}