package dev.boostio.events;

import dev.boostio.ChatPro;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ChatPro.getInstance().getPlayerData().remove(event.getPlayer().getUniqueId());
    }
}