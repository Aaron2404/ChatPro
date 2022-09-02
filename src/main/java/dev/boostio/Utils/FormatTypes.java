package dev.boostio.Utils;

public class FormatTypes {
    public static String setFormat(String output) {
        String f1 = output.replace("&k", "§k"); //Obfuscated
        String f2 = f1.replace("&l", "§l"); //Bold
        String f3 = f2.replace("&m", "§m"); //Strikethrough
        String f4 = f3.replace("&n", "§n"); //Underline
        String f5 = f4.replace("&o", "§o"); //Italic
        String f6 = f5.replace("&r", "§r"); //Reset

        return f6;
    }
}
