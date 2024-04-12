package dev.boostio.utils;

public class FormatTypes {
    // Replace '&'with the '§' chat code symbol.
    public static String setFormat(String output) {
        return output.replace("&", "§");
    }
}
