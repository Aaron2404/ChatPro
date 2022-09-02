package dev.boostio.Events;

import dev.boostio.ChatPro;
import dev.boostio.Utils.ColoringUtils;
import dev.boostio.Utils.FormatTypes;
import dev.boostio.Utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (ChatPro.colorCodes) {
            if (player.hasPermission("chatpro.colors")) {
                event.setMessage(ColoringUtils.setColor(event.getMessage()));
            }
            if (player.hasPermission("chatpro.formats")) {
                event.setMessage(FormatTypes.setFormat(event.getMessage()));
            }
        }

        PlayerData data = ChatPro.getInstance().getPlayerData().get(player.getUniqueId());

        ChatColor chatColor = data.getChatColorName();
        event.setFormat(chatColor + "" + ChatColor.BOLD + player.getDisplayName() + ChatColor.WHITE + ": " + event.getMessage());
    }
}
