package dev.boostio.Utils;

import org.bukkit.ChatColor;

public class ColoringUtils {

    // Replace '&'with the '§' chat code symbol.
    public static String setColor(String output) {
        return output.replace("&", "§");
    }

    public static ChatColor convertColor(String chatColorName) {
        if (chatColorName.equals(NameColorEnum.Default.toString()))
            return ChatColor.WHITE;
        if (chatColorName.contains(NameColorEnum.Gray.toString()))
            return ChatColor.GRAY;
        if (chatColorName.contains(NameColorEnum.Aqua.toString()))
            return ChatColor.AQUA;
        if (chatColorName.contains(NameColorEnum.Green.toString()))
            return ChatColor.GREEN;
        return ChatColor.GRAY;
    }
}
