package dev.boostio.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.boostio.ChatPro;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

// Work in Progress
public class UpdateChecker {

    static String prefix = ChatPro.PREFIX;

    @SneakyThrows
    public static void checkForUpdate() {
        String version = ChatPro.getInstance().getDescription().getVersion();
        String parseVersion = version.replace(".", "");

        String tagName;
        URL api = new URL("https://api.github.com/repos/BoostioAaron/ChatPro/releases/latest");
        URLConnection con = api.openConnection();
        con.setConnectTimeout(15000);
        con.setReadTimeout(15000);

        JsonObject json = new JsonParser().parse(new InputStreamReader(con.getInputStream())).getAsJsonObject();
        tagName = json.get("tag_name").getAsString();

        String parsedTagName = tagName.replace(".", "");

        int latestVersion = Integer.parseInt(parsedTagName.substring(1));

        if (latestVersion > Integer.parseInt(parseVersion)) {
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "Found a new version " + ChatColor.RED + tagName);
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "https://github.com/BoostioAaron/ChatPro/releases/latest");
        } else if (latestVersion < Integer.parseInt(parseVersion)) {
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "You are running an unreleased version. You must be someone special! ;-)");
        }
    }
}
