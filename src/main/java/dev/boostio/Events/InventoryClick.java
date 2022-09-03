package dev.boostio.Events;

import dev.boostio.ChatPro;
import dev.boostio.Commands.NameColor;
import dev.boostio.Utils.ColoringUtils;
import dev.boostio.Utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class InventoryClick implements Listener {
    @EventHandler
    public void onColorSelect(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!event.getInventory().equals(NameColor.colorSelectionMenu)) {
            return;
        }
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;
        event.setCancelled(true);

        if (!event.getClickedInventory().getName().equals("Colors")) {
            return;
        }

        String chatColorName = event.getCurrentItem().getItemMeta().getDisplayName();
        ChatColor chatColor = ColoringUtils.convertColor(chatColorName);

        PlayerData data = ChatPro.getInstance().getPlayerData().get(player.getUniqueId());
        data.setChatColorName(chatColor);

        player.sendMessage("Selected color: " + chatColor + ChatColor.BOLD + chatColorName);
        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

        player.closeInventory();
    }
}
