package dev.boostio.managers;

import co.aikar.commands.PaperCommandManager;
import com.github.retrooper.packetevents.PacketEvents;
import dev.boostio.ChatPro;
import dev.boostio.commands.NameColorCommand;
import dev.boostio.events.AsyncPlayerChat;
import dev.boostio.events.AsyncPlayerPreLogin;
import dev.boostio.events.InventoryClick;
import dev.boostio.events.PlayerQuit;
import dev.boostio.packetlistener.PlayerChatListener;

/**
 * Manages the start-up processes of the plugin, including the registration of commands and events.
 */
public class StartupManager {

    private final ChatPro plugin;
    private final PaperCommandManager manager;

    /**
     * Creates a new StartUpManager instance.
     *
     * @param plugin the instance of the plugin class.
     */
    public StartupManager(ChatPro plugin) {
        this.plugin = plugin;
        this.manager = plugin.getCommandManager();

        load();
    }

    /**
     * Calls methods to register commands and events.
     */
    private void load() {
        registerCommands();
        registerEvents();
        registerPacketListener();
    }

    /**
     * Registers commands related to the plugin.
     */
    private void registerCommands() {
        manager.registerCommand(new NameColorCommand(this.plugin));
    }

    /**
     * Registers events related to the plugin.
     */
    private void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerPreLogin(this.plugin), this.plugin);
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerChat(this.plugin), this.plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuit(), this.plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryClick(this.plugin), this.plugin);
    }

    private void registerPacketListener() {
        PacketEvents.getAPI().getEventManager().registerListener(new PlayerChatListener());
    }
}