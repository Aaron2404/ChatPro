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
import io.github.retrooper.packetevents.util.FoliaCompatUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * This class is responsible for notifying when an update is available.
 */
public class UpdateNotifier implements Listener {

    private final ChatPro plugin;
    private final BukkitAudiences adventure;
    private final Component updateComponent;

    /**
     * Constructor for the UpdateNotifier
     *
     * @param plugin        instance of AntiHealthIndicator plugin
     * @param latestVersion the latest version of the plugin
     */
    public UpdateNotifier(ChatPro plugin, String latestVersion) {
        this.plugin = plugin;
        this.adventure = plugin.getAdventure();

        // preparing the update notification message
        this.updateComponent = Component.text()
                .append(Component.text("[ChatPro] ", NamedTextColor.RED)
                        .decoration(TextDecoration.BOLD, true))
                .append(Component.text("Version " + latestVersion + " is ", NamedTextColor.GREEN))
                .append(Component.text("now available", NamedTextColor.GREEN)
                        .decorate(TextDecoration.UNDERLINED)
                        .hoverEvent(HoverEvent.showText(Component.text("Click to download", NamedTextColor.GREEN)))
                        .clickEvent(ClickEvent.openUrl("https://github.com/Aaron2404/ChatPro/releases/latest")))
                .append(Component.text("!", NamedTextColor.GREEN))
                .build();
    }

    /**
     * Event listener for player joining the game
     *
     * @param event encapsulates information about the player join event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("chatpro.notify")) {
            FoliaCompatUtil.runTaskTimerAsync(this.plugin, object -> {
                adventure.player(player).sendMessage(updateComponent);
            }, 40L, 20L * 60 * 60);
        }
    }
}