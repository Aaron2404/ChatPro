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
