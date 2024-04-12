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

import java.util.List;

public class AsyncPlayerChat implements Listener {
    private final ConfigManager configManager;

    /**
     * Constructor for AsyncPlayerChat.
     * @param plugin The ChatPro plugin instance.
     */
    public AsyncPlayerChat(ChatPro plugin){
        this.configManager = plugin.getConfigManager();
    }

    /**
     * Event handler for AsyncPlayerChatEvent.
     * @param event The AsyncPlayerChatEvent instance.
     */
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

    /**
     * Replaces colors and formats in the player's message.
     * @param event The AsyncPlayerChatEvent instance.
     * @param player The Player instance.
     */
    private void replaceColorsAndFormats(AsyncPlayerChatEvent event, Player player) {
        if (configManager.getConfigurationOption(ConfigOption.COLOURS_ENABLED)) {
            if (player.hasPermission("chatpro.colors")) {
                event.setMessage(ColoringUtils.setColor(event.getMessage()));
            }
            if (player.hasPermission("chatpro.formats")) {
                event.setMessage(FormatTypes.setFormat(event.getMessage()));
            }
        }
    }

    /**
     * Formats the player's message.
     * @param event The AsyncPlayerChatEvent instance.
     * @param player The Player instance.
     */
    private void formatMessage(AsyncPlayerChatEvent event, Player player) {
        PlayerData data = ChatPro.getInstance().getPlayerData().get(player.getUniqueId());
        ChatColor chatColor = data.getChatColorName();

        String format = "<" + chatColor + player.getDisplayName() + ChatColor.WHITE + "> " + event.getMessage();

        if (configManager.getConfigurationOption(ConfigOption.BETTER_MESSAGE_FORMAT)) {
            format = chatColor + player.getDisplayName() + ChatColor.WHITE + ": " + event.getMessage();
        }

        event.setFormat(format);
    }

    /**
     * Filters IPs in the player's message.
     * @param event The AsyncPlayerChatEvent instance.
     * @param player The Player instance.
     */
    private void filterIPs(AsyncPlayerChatEvent event, Player player) {
        String playerMessage = event.getMessage().toLowerCase();
        String[] splitWords = playerMessage.split(" ");

        if (configManager.getConfigurationOption(ConfigOption.FILTER_IP)) {
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

    /**
     * Filters words in the player's message.
     * @param event The AsyncPlayerChatEvent instance.
     * @param player The Player instance.
     */
    private void filterWords(AsyncPlayerChatEvent event, Player player) {
        String[] filteredWords = getFilteredWords();
        String playerMessage = event.getMessage().toLowerCase();

        for (String word : filteredWords) {
            String filterWordString = word.toLowerCase();
            if (playerMessage.contains(filterWordString)) {
                if (getBlockMessage()) {
                    player.sendMessage(ChatColor.RED + getBlockedMessageNotification());
                    event.setCancelled(true);
                }

                if (getReplaceWordInMessage()) {
                    String replacePlayerMessage = playerMessage.replace(filterWordString, getFilteredWordReplacement());
                    event.setMessage(replacePlayerMessage);
                }
            }
        }
    }

    /**
     * Returns the list of filtered words.
     * @return An array of filtered words.
     */
    private String[] getFilteredWords() {
        List<String> filteredWordsList = configManager.getConfigurationOption(ConfigOption.FILTERED_WORDS);
        return filteredWordsList.toArray(new String[0]);
    }

    /**
     * Returns the block message configuration option.
     * @return A boolean indicating whether the block message option is enabled.
     */
    private boolean getBlockMessage() {
        return configManager.getConfigurationOption(ConfigOption.BLOCK_MESSAGE);
    }

    /**
     * Returns the replacement word in message configuration option.
     * @return A boolean indicating whether the replacement word in message option is enabled.
     */
    private boolean getReplaceWordInMessage() {
        return configManager.getConfigurationOption(ConfigOption.REPLACE_WORD_IN_MESSAGE);
    }

    /**
     * Returns the filtered word replacement configuration option.
     * @return A string representing the filtered word replacement.
     */
    private String getFilteredWordReplacement() {
        return configManager.getConfigurationOption(ConfigOption.FILTERED_WORD_REPLACEMENT);
    }

    /**
     * Returns the blocked message notification configuration option.
     * @return A string representing the blocked message notification.
     */
    private String getBlockedMessageNotification() {
        return configManager.getConfigurationOption(ConfigOption.BLOCKED_MESSAGE_NOTIFICATION);
    }
}