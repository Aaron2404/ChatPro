package dev.boostio.Utils;

import org.bukkit.ChatColor;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ColorCharParser {

    public static void ColorCodes(AsyncPlayerChatEvent event){
        //1 - 9
        colors("&0", ChatColor.BLACK, event);
        colors("&1", ChatColor.DARK_BLUE, event);
        colors("&2", ChatColor.DARK_GREEN, event);
        colors("&3", ChatColor.DARK_AQUA, event);
        colors("&4", ChatColor.DARK_RED, event);
        colors("&5", ChatColor.DARK_PURPLE, event);
        colors("&6", ChatColor.GOLD, event);
        colors("&7", ChatColor.GRAY, event);
        colors("&8", ChatColor.DARK_GRAY, event);
        colors("&9", ChatColor.BLUE, event);

        //A - F
        colors("&a", ChatColor.GREEN, event);
        colors("&b", ChatColor.AQUA, event);
        colors("&c", ChatColor.RED, event);
        colors("&d", ChatColor.LIGHT_PURPLE, event);
        colors("&e", ChatColor.YELLOW, event);
        colors("&f", ChatColor.WHITE, event);

        // L - R
        colors("&l", ChatColor.BOLD, event);
        colors("&m", ChatColor.STRIKETHROUGH, event);
        colors("&n", ChatColor.UNDERLINE, event);
        colors("&o", ChatColor.ITALIC, event);
        colors("&r", ChatColor.RESET, event);
    }


    public static void colors(String chatCode, ChatColor chatColorName, AsyncPlayerChatEvent event){
        if(event.getMessage().contains(chatCode)){
            String newMessage = event.getMessage().replace(chatCode, "" + chatColorName);
            event.setMessage(newMessage);
        }
    }
}
