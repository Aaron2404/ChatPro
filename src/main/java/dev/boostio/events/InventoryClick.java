package dev.boostio.events;

import dev.boostio.ChatPro;
import dev.boostio.commands.NameColorCommand;
import dev.boostio.utils.ColoringUtils;
import dev.boostio.utils.PlayerData;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;


public class InventoryClick implements Listener {
    private final BukkitAudiences adventure;

    public InventoryClick(ChatPro plugin) {
        this.adventure = plugin.getAdventure();
    }


    /**
     * Event handler for the InventoryClickEvent.
     * This method is called when a player clicks on an item in an inventory.
     * If the inventory is a color selection inventory, the clicked color is set as the player's chat color.
     *
     * @param event The InventoryClickEvent that occurred.
     */
    @EventHandler
    public void onColorSelect(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView inventoryView = player.getOpenInventory();

        if (!inventoryView.getTitle().equals(NameColorCommand.colorSelectionTitle)) return;

        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null || currentItem.getItemMeta() == null) return;

        String chatColorName = currentItem.getItemMeta().getDisplayName();
        ChatColor chatColor = ColoringUtils.convertColor(chatColorName);

        setPlayerChatColor(player, chatColor);

        adventure.sender(player).sendMessage(Component.text("Selected color: ")
                 .append(Component.text(chatColor + chatColorName)
                 .decoration(TextDecoration.BOLD, true)));
        player.closeInventory();
    }

    /**
     * Sets the chat color of the given player.
     *
     * @param player    The player whose chat color to set.
     * @param chatColor The chat color to set.
     */
    private void setPlayerChatColor(Player player, ChatColor chatColor) {
        PlayerData data = ChatPro.getInstance().getPlayerData().get(player.getUniqueId());
        data.setChatColorName(chatColor);
    }
}
