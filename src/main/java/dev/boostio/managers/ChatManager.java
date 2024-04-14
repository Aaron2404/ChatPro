package dev.boostio.managers;

import dev.boostio.ChatPro;
import dev.boostio.enums.ConfigOption;
import dev.boostio.utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChatManager {
    private final ConfigManager configManager;
    private final String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    private final Pattern pattern = Pattern.compile(IPV4_PATTERN);

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
        handleFilter(event, player);

        // Handle colors and formats.
        handleColourAndFormat(event, player);
    }

    /**
     * Add colour and formatting to the player's chat message.
     * @param event The AsyncPlayerChatEvent instance.
     * @param player The Player instance.
     */
    public void handleColourAndFormat(AsyncPlayerChatEvent event, Player player) {
        replaceColorsAndFormats(event, player);
        formatMessage(event, player);
    }

    /**
     * Filter the player's chat message.
     * @param event The AsyncPlayerChatEvent instance.
     * @param player The Player instance.
     */
    public void handleFilter(AsyncPlayerChatEvent event, Player player) {
        filterIPs(event, player);
        filterWords(event, player);
    }

    /**
     * Replaces colors and formats in the player's message.
     * @param event The AsyncPlayerChatEvent instance.
     * @param player The Player instance.
     */
    private void replaceColorsAndFormats(AsyncPlayerChatEvent event, Player player) {
        if (configManager.getConfigurationOption(ConfigOption.COLOURS_ENABLED)) {
            if (player.hasPermission("chatpro.colors")) {
                event.setMessage(setFormatting(event.getMessage()));
            }
            if (player.hasPermission("chatpro.formats")) {
                event.setMessage(setFormatting(event.getMessage()));
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

    // Replace '&'with the '§' chat code symbol.
    public static String setFormatting(String output) {
        return output.replace("&", "§");
    }

    /**
     * Filters IPs in the player's message.
     * @param event The AsyncPlayerChatEvent instance.
     * @param player The Player instance.
     */
    private void filterIPs(AsyncPlayerChatEvent event, Player player) {
        if (configManager.getConfigurationOption(ConfigOption.FILTER_IP)) {
            for (String word : event.getMessage().toLowerCase().split(" ")) {
                if (IsIpv4Address(word)) {
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

    private boolean IsIpv4Address(final String message) {
        Matcher matcher = pattern.matcher(message);
        return matcher.matches();
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