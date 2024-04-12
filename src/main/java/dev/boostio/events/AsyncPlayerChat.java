package dev.boostio.events;

import dev.boostio.ChatPro;
import dev.boostio.enums.ConfigOption;
import dev.boostio.managers.ConfigManager;
import dev.boostio.utils.ColoringUtils;
import dev.boostio.utils.FormatTypes;
import dev.boostio.utils.IPv4ValidatorRegex;
import dev.boostio.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class AsyncPlayerChat implements Listener {
    private final ConfigManager configManager;

    public AsyncPlayerChat(ChatPro plugin){
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        // Handle filter.
        filterIPs(event, player);
        filterWords(event, player);

        // Handle colors and formats.
        replaceColorsAndFormats(event, player);
        formatMessage(event, player);
    }



    private void replaceColorsAndFormats(AsyncPlayerChatEvent event, Player player) {
        if (getColoursEnabled()) {
            if (player.hasPermission("chatpro.colors")) {
                event.setMessage(ColoringUtils.setColor(event.getMessage()));
            }
            if (player.hasPermission("chatpro.formats")) {
                event.setMessage(FormatTypes.setFormat(event.getMessage()));
            }
        }
    }

    private void formatMessage(AsyncPlayerChatEvent event, Player player) {
        PlayerData data = ChatPro.getInstance().getPlayerData().get(player.getUniqueId());
        ChatColor chatColor = data.getChatColorName();

        String format = "<" + chatColor + player.getDisplayName() + ChatColor.WHITE + "> " + event.getMessage();

        if (getBetterMessageFormat()) {
            format = chatColor + player.getDisplayName() + ChatColor.WHITE + ": " + event.getMessage();
        }

        event.setFormat(format);
    }

    private void filterIPs(AsyncPlayerChatEvent event, Player player) {
        String playerMessage = event.getMessage().toLowerCase();
        String[] splitWords = playerMessage.split(" ");

        if (getFilterIp()) {
            for (String separateWord : splitWords) {
                boolean ipFound = IPv4ValidatorRegex.isValid(separateWord);
                if (ipFound) {
                    if (getBlockMessage())
                    {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "Do not send any IP addresses in the chat!");
                        return;
                    }
                    if (getReplaceWordInMessage()) {
                        String replacePlayerMessage = playerMessage.replace(IPv4ValidatorRegex.ipMessage, getFilteredWordReplacement());
                        event.setMessage(replacePlayerMessage);
                    }
                }
            }
        }
    }

    private void filterWords(AsyncPlayerChatEvent event, Player player) {
        String[] filteredWords = getFilteredWords();
        String playerMessage = event.getMessage().toLowerCase();

        for (String word : filteredWords) {
            String filterWordString = word.toLowerCase();
            if (playerMessage.contains(filterWordString)) {
                if (getBlockMessage()) {
                    player.sendMessage(getBlockedMessageNotification());
                    event.setCancelled(true);
                }

                if (getReplaceWordInMessage()) {
                    String replacePlayerMessage = playerMessage.replace(filterWordString, getFilteredWordReplacement());
                    event.setMessage(replacePlayerMessage);
                }
            }
        }
    }

    private String[] getFilteredWords() {
        List<String> filteredWordsList = configManager.getConfigurationOption(ConfigOption.FILTERED_WORDS);
        return filteredWordsList.toArray(new String[0]);
    }

    private boolean getFilterIp() {
        return configManager.getConfigurationOption(ConfigOption.FILTER_IP);
    }

    private boolean getBlockMessage() {
        return configManager.getConfigurationOption(ConfigOption.BLOCK_MESSAGE);
    }

    private boolean getReplaceWordInMessage() {
        return configManager.getConfigurationOption(ConfigOption.REPLACE_WORD_IN_MESSAGE);
    }

    private String getFilteredWordReplacement() {
        return configManager.getConfigurationOption(ConfigOption.FILTERED_WORD_REPLACEMENT);
    }

    private String getBlockedMessageNotification() {
        return configManager.getConfigurationOption(ConfigOption.BLOCKED_MESSAGE_NOTIFICATION);
    }

    private boolean getBetterMessageFormat() {
        return configManager.getConfigurationOption(ConfigOption.BETTER_MESSAGE_FORMAT);
    }

    private boolean getColoursEnabled() {
        return configManager.getConfigurationOption(ConfigOption.COLOURS_ENABLED);
    }
}