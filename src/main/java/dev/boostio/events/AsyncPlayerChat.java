package dev.boostio.events;

import dev.boostio.ChatPro;
import dev.boostio.managers.ChatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {
    private final ChatManager chatManager;

    /**
     * Constructor for AsyncPlayerChat.
     * @param plugin The ChatPro plugin instance.
     */
    public AsyncPlayerChat(ChatPro plugin){
        this.chatManager = plugin.getChatManager();
    }

    /**
     * Event handler for AsyncPlayerChatEvent.
     * @param event The AsyncPlayerChatEvent instance.
     */
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        this.chatManager.handleChat(event);
    }
}