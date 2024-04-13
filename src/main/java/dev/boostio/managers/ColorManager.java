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

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import dev.boostio.enums.NameColorEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColorManager {
    public static String colorSelectionTitle = "Color Selector GUI";
    private final Inventory colorSelectionMenu = Bukkit.createInventory(null, 9, "Color Selector GUI");

    public ColorManager() {
        initializeInventory();
    }

    public Inventory getColorSelectionMenu() {
        return colorSelectionMenu;
    }

    /**
     * Creates an ItemStack representing a color.
     *
     * @param nameColor   The color to create an ItemStack for.
     * @param woolVariant The wool variant to use for the ItemStack.
     * @return The ItemStack representing the color.
     */
    private ItemStack createColorItem(NameColorEnum nameColor, int woolVariant) {
        ChatColor chatColor = convertColor(nameColor.toString());
        ItemStack colorItem = getColouredWool(chatColor, woolVariant);
        ItemMeta colorItemMeta = colorItem.getItemMeta();

        colorItemMeta.setDisplayName(chatColor + nameColor.toString());
        colorItemMeta.setLore(Collections.singletonList(ChatColor.BLUE + "Click to select color"));

        colorItem.setItemMeta(colorItemMeta);
        return colorItem;
    }


    /**
     * Creates an ItemStack representing a colored wool block.
     *
     * @param chatColor   The color of the wool block.
     * @param woolVariant The wool variant to use for the ItemStack.
     * @return The ItemStack representing the colored wool block.
     */
    private ItemStack getColouredWool(ChatColor chatColor, int woolVariant) {
        Material woolMaterial = Material.valueOf("WOOL"); // Default to legacy wool if the color is not found
        if (PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_13_1)) {
            try {
                woolMaterial = Material.valueOf(chatColor.name() + "_WOOL");
            } catch (IllegalArgumentException ignored) {
            }
        } else {
            return new ItemStack(Material.valueOf("WOOL"), 1, (short) woolVariant);
        }
        return new ItemStack(woolMaterial, 1);
    }

    public ChatColor convertColor(String chatColorName) {
        if (chatColorName.equals(NameColorEnum.Default.toString()))
            return ChatColor.WHITE;
        if (chatColorName.contains(NameColorEnum.Gray.toString()))
            return ChatColor.GRAY;
        if (chatColorName.contains(NameColorEnum.Aqua.toString()))
            return ChatColor.AQUA;
        if (chatColorName.contains(NameColorEnum.Green.toString()))
            return ChatColor.GREEN;
        return ChatColor.GRAY;
    }

    /**
     * Initializes the color selection inventory with ItemStacks representing different colors.
     * This method is called once during the command initialization to prevent it from being called each time the command is executed.
     * It creates ItemStacks for each color and adds them to the color selection inventory.
     */
    private void initializeInventory() {
        List<ItemStack> colorItems = new ArrayList<>();
        colorItems.add(createColorItem(NameColorEnum.Default, 0));
        colorItems.add(createColorItem(NameColorEnum.Aqua, 3));
        colorItems.add(createColorItem(NameColorEnum.Green, 5));
        colorItems.add(createColorItem(NameColorEnum.Gray, 8));

        for (int i = 0; i < colorItems.size(); i++) {
            colorSelectionMenu.setItem(i, colorItems.get(i));
        }
    }
}