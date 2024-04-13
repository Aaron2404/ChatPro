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
import dev.boostio.managers.ColorManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;


public class InventoryClick implements Listener {
    private final BukkitAudiences adventure;
    private final ColorManager colorManager;

    public InventoryClick(ChatPro plugin) {
        this.adventure = plugin.getAdventure();
        this.colorManager = plugin.getColorManager();
    }

    /**
     * Event handler for the InventoryClickEvent.
     * This method is called when a player clicks on an item in an inventory.
     * If the inventory is a color selection inventory, the clicked color is set as the player's chat color.
     *
     * @param event The InventoryClickEvent that occurred.
     */
    @EventHandler
    public void onColorSelect(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!player.getOpenInventory().getTitle().equals(ColorManager.colorSelectionTitle)) return;

        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null || currentItem.getItemMeta() == null) return;

        String chatColorName = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName());
        ChatColor chatColor = this.colorManager.convertColor(chatColorName);
        setPlayerChatColor(player, chatColor);

        Component csComponent = Component.text("Selected color: ")
                .append(Component.text(chatColorName, NamedTextColor.NAMES.value(chatColor.name().toLowerCase())));

        adventure.sender(player).sendMessage(csComponent);
        player.closeInventory();
    }

    /**
     * Sets the chat color of the given player.
     *
     * @param player    The player whose chat color to set.
     * @param chatColor The chat color to set.
     */
    private void setPlayerChatColor(Player player, ChatColor chatColor) {
        ChatPro.getInstance().getPlayerData().get(player.getUniqueId()).setChatColorName(chatColor);
    }
}
