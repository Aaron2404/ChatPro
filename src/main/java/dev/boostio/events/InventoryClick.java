package dev.boostio.events;

import dev.boostio.ChatPro;
import dev.boostio.utils.ColoringUtils;
import dev.boostio.utils.PlayerData;
import org.bukkit.ChatColor;
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

        if (!inventoryView.getTitle().equals("Color Selector GUI"))
            return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null)
            return;

        String chatColorName = event.getCurrentItem().getItemMeta().getDisplayName();
        ChatColor chatColor = ColoringUtils.convertColor(chatColorName);

        PlayerData data = ChatPro.getInstance().getPlayerData().get(player.getUniqueId());
        data.setChatColorName(chatColor);

        player.sendMessage("Selected color: " + chatColor + ChatColor.BOLD + chatColorName);
        //player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1, 1);

        player.closeInventory();
    }
}
