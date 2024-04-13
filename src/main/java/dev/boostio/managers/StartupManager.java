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

import co.aikar.commands.PaperCommandManager;
import dev.boostio.ChatPro;
import dev.boostio.commands.NameColorCommand;
import dev.boostio.events.AsyncPlayerChat;
import dev.boostio.events.AsyncPlayerPreLogin;
import dev.boostio.events.InventoryClick;
import dev.boostio.events.PlayerQuit;

/**
 * Manages the start-up processes of the plugin, including the registration of commands and events.
 */
public class StartupManager {

    private final ChatPro plugin;
    private final PaperCommandManager manager;

    /**
     * Creates a new StartUpManager instance.
     *
     * @param plugin the instance of the plugin class.
     */
    public StartupManager(ChatPro plugin) {
        this.plugin = plugin;
        this.manager = plugin.getCommandManager();

        load();
    }

    /**
     * Calls methods to register commands and events.
     */
    private void load() {
        registerCommands();
        registerEvents();
    }

    /**
     * Registers commands related to the plugin.
     */
    private void registerCommands() {
        manager.registerCommand(new NameColorCommand(this.plugin));
    }

    /**
     * Registers events related to the plugin.
     */
    private void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerPreLogin(this.plugin), this.plugin);
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerChat(this.plugin), this.plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuit(), this.plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryClick(this.plugin), this.plugin);
    }
}