package dev.boostio.Events;

import dev.boostio.ChatPro;
import dev.boostio.Utils.ColoringUtils;
import dev.boostio.Utils.FormatTypes;
import dev.boostio.Utils.IPv4ValidatorRegex;
import dev.boostio.Utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {

    @EventHandler
    public void onAsyncPlayerChatColourReplace(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        /// Replacing each char code in a message using the setColor Util.
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

        Bukkit.getLogger().warning(data.getChatColorName().toString());

        String format = "<" + chatColor + player.getDisplayName() + ChatColor.WHITE + "> " + event.getMessage();

        if (ChatPro.betterMessageFormat) {
            format = chatColor + player.getDisplayName() + ChatColor.WHITE + ": " + event.getMessage();
        }

        //Player name format
        event.setFormat(format);
    }

    @EventHandler
    public void onAsyncPlayerChatWordFilter(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        //Turn the list into an array.
        Object[] filteredWords = ChatPro.filteredWords.toArray();
        String playerMessage = event.getMessage().toLowerCase();

        String[] splitWords = playerMessage.split(" ");

        if (ChatPro.filterIPs) {
            for (String separateWord : splitWords) {
                boolean ipFound = IPv4ValidatorRegex.isValid(separateWord);
                if (ipFound) {
                    if (ChatPro.blockMessage) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "Do not send any IP addresses in the chat!");
                        return;
                    }
                    if (ChatPro.replaceWordInMessage) {
                        String replacePlayerMessage = playerMessage.replace(IPv4ValidatorRegex.ipMessage, ChatPro.filteredWordReplacement);
                        event.setMessage(replacePlayerMessage);
                    }
                }
            }
        }

        //Simple for loop to check if the message sent by the player contains a word that on the filter list.
        for (Object word : filteredWords) {
            CharSequence filteredWord = (CharSequence) word;
            String filterWordString = filteredWord.toString().toLowerCase();
            if (playerMessage.contains(filterWordString)) {
                if (ChatPro.blockMessage) {
                    player.sendMessage(ChatPro.blockedMessageNotification);
                    event.setCancelled(true);
                }
                if (ChatPro.replaceWordInMessage) {
                    String replacePlayerMessage = playerMessage.replace(filterWordString, ChatPro.filteredWordReplacement);
                    event.setMessage(replacePlayerMessage);
                }
            }
        }
    }
}
