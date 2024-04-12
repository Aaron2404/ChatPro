package dev.boostio.events;

import dev.boostio.ChatPro;
import dev.boostio.enums.ConfigOption;
import dev.boostio.managers.ConfigManager;
import dev.boostio.utils.ColoringUtils;
import dev.boostio.utils.FormatTypes;
import dev.boostio.utils.IPv4ValidatorRegex;
import dev.boostio.utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {
    private final ChatPro plugin;
    private final ConfigManager configManager;

    public AsyncPlayerChat(ChatPro plugin){
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onAsyncPlayerChatColourReplace(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        /// Replacing each char code in a message using the setColor Util.
        if (configManager.getConfigurationOption(ConfigOption.COLOURS_ENABLED)) {
            if (player.hasPermission("chatpro.colors")) {
                event.setMessage(ColoringUtils.setColor(event.getMessage()));
            }
            if (player.hasPermission("chatpro.formats")) {
                event.setMessage(FormatTypes.setFormat(event.getMessage()));
            }
        }

        PlayerData data = ChatPro.getInstance().getPlayerData().get(player.getUniqueId());
        ChatColor chatColor = data.getChatColorName();

        String format = "<" + chatColor + player.getDisplayName() + ChatColor.WHITE + "> " + event.getMessage();

        if (configManager.getConfigurationOption(ConfigOption.BETTER_MESSAGE_FORMAT)) {
            format = chatColor + player.getDisplayName() + ChatColor.WHITE + ": " + event.getMessage();
        }

        //Player name format
        event.setFormat(format);
    }

    @EventHandler
    public void onAsyncPlayerChatWordFilter(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        //Turn the list into an array.
        Object[] filteredWords = configManager.getConfigurationOption(ConfigOption.FILTERED_WORDS);
        String playerMessage = event.getMessage().toLowerCase();

        String[] splitWords = playerMessage.split(" ");

        if (configManager.getConfigurationOption(ConfigOption.FILTER_IP)) {
            for (String separateWord : splitWords) {
                boolean ipFound = IPv4ValidatorRegex.isValid(separateWord);
                if (ipFound) {
                    if (configManager.getConfigurationOption(ConfigOption.BLOCK_MESSAGE)) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "Do not send any IP addresses in the chat!");
                        return;
                    }
                    if (configManager.getConfigurationOption(ConfigOption.REPLACE_WORD_IN_MESSAGE)) {
                        String replacePlayerMessage = playerMessage.replace(IPv4ValidatorRegex.ipMessage, configManager.getConfigurationOption(ConfigOption.FILTERED_WORD_REPLACEMENT));
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
                if (configManager.getConfigurationOption(ConfigOption.BLOCK_MESSAGE)) {
                    player.sendMessage((String) configManager.getConfigurationOption(ConfigOption.BLOCKED_MESSAGE_NOTIFICATION));
                    event.setCancelled(true);
                }
                if (configManager.getConfigurationOption(ConfigOption.REPLACE_WORD_IN_MESSAGE)) {
                    String replacePlayerMessage = playerMessage.replace(filterWordString, configManager.getConfigurationOption(ConfigOption.FILTERED_WORD_REPLACEMENT));
                    event.setMessage(replacePlayerMessage);
                }
            }
        }
    }
}
