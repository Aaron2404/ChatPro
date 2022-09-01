package dev.boostio.event;

import dev.boostio.ChatPro;
import dev.boostio.Commands.NameColor;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class ChooseColor implements Listener {

    @EventHandler
    public void OnClick(InventoryClickEvent event) {

        if (!event.getInventory().equals(NameColor.colorSelectionMenu)) {
            return;
        }
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;
        event.getCurrentItem().getItemMeta().getDisplayName();

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Default")){
            ChatPro.getInstance().getPlayerData().get(player.getUniqueId()).setChatColorName(ChatColor.WHITE);
            player.sendMessage("Selected color:" + ChatColor.WHITE + ChatColor.BOLD + "Default");
            player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        }
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Aqua")){
            ChatPro.getInstance().getPlayerData().get(player.getUniqueId()).setChatColorName(ChatColor.AQUA);
            player.sendMessage("Selected color:" + ChatColor.AQUA + ChatColor.BOLD + "Aqua");
            player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        }
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Green")){
            ChatPro.getInstance().getPlayerData().get(player.getUniqueId()).setChatColorName(ChatColor.GREEN);
            player.sendMessage("Selected color:" + ChatColor.GREEN + ChatColor.BOLD + "Green");
            player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        }
    }
}
