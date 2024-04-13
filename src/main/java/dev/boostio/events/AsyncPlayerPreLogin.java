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

