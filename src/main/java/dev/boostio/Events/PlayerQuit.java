package dev.boostio.Events;

import dev.boostio.ChatPro;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ChatPro.getInstance().getPlayerData().remove(player.getUniqueId());
    }
}