package dev.boostio.Events;

import dev.boostio.ChatPro;
import dev.boostio.Utils.ColoringUtils;
import dev.boostio.Utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;


public class InventoryClick implements Listener {
    @EventHandler
    public void onColorSelect(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView inventoryView = player.getOpenInventory();

        if (!inventoryView.getTopInventory().getName().equals("Color Selector GUI"))
            return;

        event.setCancelled(true);

        if (!event.getClickedInventory().getName().equals("Color Selector GUI"))
            return;

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null)
            return;


        String chatColorName = event.getCurrentItem().getItemMeta().getDisplayName();
        ChatColor chatColor = ColoringUtils.convertColor(chatColorName);

        PlayerData data = ChatPro.getInstance().getPlayerData().get(player.getUniqueId());
        data.setChatColorName(chatColor);

        player.sendMessage("Selected color: " + chatColor + ChatColor.BOLD + chatColorName);
        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

        player.closeInventory();
    }
}
