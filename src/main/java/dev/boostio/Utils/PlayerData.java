package dev.boostio.Utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    private UUID uuid;

    ChatColor chatColorName;
}
