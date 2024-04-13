package dev.boostio.managers;

import dev.boostio.ChatPro;
import dev.boostio.enums.ConfigOption;
import dev.boostio.utils.ColoringUtils;
import dev.boostio.utils.IPv4ValidatorRegex;
import dev.boostio.utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.stream.Collectors;

public class ChatManager {
    private final ConfigManager configManager;

    public ChatManager(ChatPro plugin){
        this.configManager = plugin.getConfigManager();
    }

    /**
     * Handles the player's chat message.
     * @param event The AsyncPlayerChatEvent instance.
     */
    public void handleChat(AsyncPlayerChatEvent event) {
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
                event.setMessage(ColoringUtils.setFormatting(event.getMessage()));
            }
            if (player.hasPermission("chatpro.formats")) {
                event.setMessage(ColoringUtils.setFormatting(event.getMessage()));
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
        if (configManager.getConfigurationOption(ConfigOption.FILTER_IP)) {
            for (String word : event.getMessage().toLowerCase().split(" ")) {
                if (IPv4ValidatorRegex.isValid(word)) {
                    if (getBlockMessage()) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "Do not send any IP addresses in the chat!");
                        return;
                    }
                    if (configManager.getConfigurationOption(ConfigOption.REPLACE_WORD_IN_MESSAGE)) {
                        event.setMessage(event.getMessage().replace(word, getFilteredWordReplacement()));
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
        String playerMessage = event.getMessage().toLowerCase();
        String replacement = getFilteredWordReplacement();

        for (String word : getFilteredWords()) {
            String filterWordString = word.toLowerCase();
            String spacedFilterWord = filterWordString.chars()
                    .mapToObj(c -> String.valueOf((char) c))
                    .collect(Collectors.joining(" "));

            if (playerMessage.contains(filterWordString) || playerMessage.contains(spacedFilterWord)) {
                if (getBlockMessage()) {
                    player.sendMessage(ChatColor.RED + getBlockedMessageNotification());
                    event.setCancelled(true);
                    return;
                }

                playerMessage = playerMessage.replace(filterWordString, replacement);
                playerMessage = playerMessage.replace(spacedFilterWord, replacement);
            }
        }

        event.setMessage(playerMessage);
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