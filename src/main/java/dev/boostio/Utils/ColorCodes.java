package dev.boostio.Utils;

public class ColorCodes {
    public static String setColor(String output) {
        //Replaces each ColorCode in a player message with a chat color.
        String c1 = output.replace("&0", "§0"); //Black
        String c2 = c1.replace("&1", "§1"); //Dark Blue
        String c3 = c2.replace("&2", "§2"); //Dark Green
        String c4 = c3.replace("&3", "§3"); //Dark Aqua
        String c5 = c4.replace("&4", "§4"); //Dark Red
        String c6 = c5.replace("&5", "§5"); //Dark Purple
        String c7 = c6.replace("&6", "§6"); //Gold
        String c8 = c7.replace("&7", "§7"); //Gray
        String c9 = c8.replace("&8", "§8"); //Dark Gray
        String c10 = c9.replace("&9", "§9"); //Blue

        String c11 = c10.replace("&a", "§a"); //Green
        String c12 = c11.replace("&b", "§b"); //Aqua
        String c13 = c12.replace("&c", "§c"); //Red
        String c14 = c13.replace("&d", "§d"); //Light Purple
        String c15 = c14.replace("&e", "§e"); //Yellow
        String c16 = c15.replace("&f", "§f"); //White

        return c16;
    }
}
