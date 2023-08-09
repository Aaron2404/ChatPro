package dev.boostio.Commands;

import dev.boostio.ChatPro;
import dev.boostio.Utils.ColoringUtils;
import dev.boostio.Utils.NameColorEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NameColor implements CommandExecutor {

    public static Inventory colorSelectionMenu = Bukkit.createInventory(null, 9, "Color Selector GUI");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players are allowed to execute this command :(");
            return false;
        }

        if (!sender.hasPermission("chatpro.colorcodes")) {
            sender.sendMessage(ColoringUtils.setColor(ChatPro.colorCommandNoPermission));
            return false;
        }

        Player player = (Player) sender;

        player.openInventory(colorSelectionMenu);
        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);

        ItemStack defaultColor = CreateColorItem(NameColorEnum.Default, ChatColor.WHITE, 0);
        ItemStack lightBlueColor = CreateColorItem(NameColorEnum.Aqua, ChatColor.AQUA, 3);
        ItemStack greenColor = CreateColorItem(NameColorEnum.Green, ChatColor.GREEN, 5);
        ItemStack grayColor = CreateColorItem(NameColorEnum.Gray, ChatColor.GRAY, 8);

        // Add Items to the class menu
        colorSelectionMenu.setItem(0, defaultColor);
        colorSelectionMenu.setItem(1, grayColor);
        colorSelectionMenu.setItem(2, lightBlueColor);
        colorSelectionMenu.setItem(3, greenColor);

        return false;
    }

    private ItemStack CreateColorItem(NameColorEnum nameColor, ChatColor chatColor, int woolVariant){
        ItemStack ColorItem = new ItemStack(Material.WOOL, 1, (short) woolVariant);
        ItemMeta ColorItemMD = ColorItem.getItemMeta();
        ColorItemMD.setDisplayName(chatColor + nameColor.toString());
        ArrayList<String> defaultLore = new ArrayList<>();
        defaultLore.add(ChatColor.BLUE + "Click to select color");
        ColorItemMD.setLore(defaultLore);
        ColorItem.setItemMeta(ColorItemMD);
        return ColorItem;
    }
}
