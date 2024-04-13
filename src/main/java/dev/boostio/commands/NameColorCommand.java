package dev.boostio.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.boostio.ChatPro;
import dev.boostio.managers.ColorManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandAlias("namecolor|nc")
@CommandPermission("chatpro.namecolor")
public class NameColorCommand extends BaseCommand {
    private final BukkitAudiences adventure;
    private final ColorManager colorManager;

    public NameColorCommand(ChatPro plugin) {
        this.adventure = plugin.getAdventure();
        this.colorManager = plugin.getColorManager();
    }

    @Default
    public void changeNameColour(CommandSender sender) {
        if (!(sender instanceof Player)) {
            adventure.sender(sender).sendMessage(Component.text("Only players are allowed to execute this command", NamedTextColor.RED));
            return;
        }

        Player player = (Player) sender;
        player.openInventory(colorManager.getColorSelectionMenu());
    }
}
