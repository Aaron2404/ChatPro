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

package dev.boostio;

import co.aikar.commands.PaperCommandManager;
import com.github.retrooper.packetevents.PacketEvents;
import dev.boostio.managers.*;
import dev.boostio.utils.PlayerData;
import io.github.retrooper.packetevents.bstats.Metrics;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

@Getter
public final class ChatPro extends JavaPlugin {
    @Getter
    private static ChatPro instance;
    private final HashMap<UUID, PlayerData> playerData = new HashMap<>();
    private ConfigManager configManager;
    private ChatManager chatManager;
    private ColorManager colorManager;
    private PaperCommandManager commandManager;
    private BukkitAudiences adventure;

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
        colorManager = new ColorManager();
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
