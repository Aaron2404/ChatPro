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

        setChatColor(event, ChatColor.WHITE, "Default");
        setChatColor(event, ChatColor.AQUA, "Aqua");
        setChatColor(event, ChatColor.GREEN, "Green");
    }
    protected void setChatColor(InventoryClickEvent event, ChatColor chatColor, String name){
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(chatColor + name)){
            ChatPro.getInstance().getPlayerData().get(player.getUniqueId()).setChatColorName(chatColor);
            player.sendMessage("Selected color:" + chatColor + ChatColor.BOLD + name);
            player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        }
    }
}
