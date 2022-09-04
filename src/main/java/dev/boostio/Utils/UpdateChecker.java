package dev.boostio.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.boostio.ChatPro;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {
    private static final String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Crasher" + ChatColor.GRAY + "] ";
    public static void checkForUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(ChatPro.getInstance(), () -> {
            String version = ChatPro.getInstance().getDescription().getVersion();
            String parseVersion = version.replace(".", "");

            String tagName;
            URL api;
            URLConnection con;
            JsonObject json;

            try {
                api = new URL("https://api.github.com/repos/BoostioAaron/ChatPro/releases/latest");
                con = api.openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(15000);
                json = new JsonParser().parse(new InputStreamReader(con.getInputStream())).getAsJsonObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            tagName = json.get("tag_name").getAsString();
            String parsedTagName = tagName.replace(".", "");

            int latestVersion = Integer.parseInt(parsedTagName.substring(1));

            if (latestVersion > Integer.parseInt(parseVersion)) {
                Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "Found a new version " + ChatColor.RED + tagName);
                Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "https://github.com/BoostioAaron/ChatPro/releases/latest");
            } else if (latestVersion < Integer.parseInt(parseVersion)) {
                Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "You are running an unreleased version. You must be someone special! ;-)");
            }
        });
    }
}
