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

package dev.boostio.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.boostio.ChatPro;
import dev.boostio.managers.ColorManager;
import org.bukkit.entity.Player;


@CommandAlias("namecolor|nc")
@CommandPermission("chatpro.namecolor")
public class NameColorCommand extends BaseCommand {
    private final ColorManager colorManager;

    public NameColorCommand(ChatPro plugin) {
        this.colorManager = plugin.getColorManager();
    }

    @Default
    public void changeNameColour(Player player) {
        player.openInventory(colorManager.getColorSelectionMenu());
    }
}
