package dev.boostio.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import dev.boostio.ChatPro;
import dev.boostio.utils.ColoringUtils;
import dev.boostio.enums.NameColorEnum;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

@CommandAlias("namecolor|nc")
@CommandPermission("chatpro.colorcodes")
public class NameColorCommand extends BaseCommand {
    private final Inventory colorSelectionMenu = Bukkit.createInventory(null, 9, "Color Selector GUI");
    private final BukkitAudiences adventure;

    public NameColorCommand(ChatPro plugin) {
        this.adventure = plugin.getAdventure();
    }

    @Default
    public void changeNameColour(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players are allowed to execute this command.");
            return;
        }

        Player player = (Player) sender;

        player.openInventory(colorSelectionMenu);
        //player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1, 1);

        // Add Items to the class menu
        colorSelectionMenu.setItem(0, CreateColorItem(NameColorEnum.Default, 0));
        colorSelectionMenu.setItem(1, CreateColorItem(NameColorEnum.Aqua,  3));
        colorSelectionMenu.setItem(2, CreateColorItem(NameColorEnum.Green,  5));
        colorSelectionMenu.setItem(3, CreateColorItem(NameColorEnum.Gray, 8));
    }

    private ItemStack CreateColorItem(NameColorEnum nameColor, int woolVariant){
        ChatColor chatColor = ColoringUtils.convertColor(nameColor.toString());
        ItemStack ColorItem = getColouredWool(chatColor, woolVariant);
        ItemMeta ColorItemMD = ColorItem.getItemMeta();
        ColorItemMD.setDisplayName(chatColor + nameColor.toString());
        ArrayList<String> defaultLore = new ArrayList<>();
        defaultLore.add(ChatColor.BLUE + "Click to select color");
        ColorItemMD.setLore(defaultLore);
        ColorItem.setItemMeta(ColorItemMD);
        return ColorItem;
    }

    private ItemStack getColouredWool(ChatColor chatColor, int woolVariant){
        ServerVersion serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
        Material woolMaterial;
        try {
            if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13_1)) {
                woolMaterial = Material.valueOf(chatColor.name() + "_WOOL");
            } else {
                return new ItemStack(Material.valueOf("WOOL"), 1, (short) woolVariant);
            }
        } catch (IllegalArgumentException e) {
            woolMaterial = Material.WHITE_WOOL; // Default to white wool if the color is not found
        }
        return new ItemStack(woolMaterial, 1);
    }
}
