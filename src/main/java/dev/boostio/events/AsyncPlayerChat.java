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
import dev.boostio.managers.ChatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {
    private final ChatManager chatManager;

    /**
     * Constructor for AsyncPlayerChat.
     *
     * @param plugin The ChatPro plugin instance.
     */
    public AsyncPlayerChat(ChatPro plugin) {
        this.chatManager = plugin.getChatManager();
    }

    /**
     * Event handler for AsyncPlayerChatEvent.
     *
     * @param event The AsyncPlayerChatEvent instance.
     */
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        this.chatManager.handleChat(event);
    }
}