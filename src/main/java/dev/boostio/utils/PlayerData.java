package dev.boostio.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    ChatColor chatColorName;
    private UUID uuid;
}
