package dev.boostio.managers;

import co.aikar.commands.PaperCommandManager;
import dev.boostio.ChatPro;
import dev.boostio.Commands.NameColorCommand;
import dev.boostio.Events.AsyncPlayerChat;
import dev.boostio.Events.AsyncPlayerPreLogin;
import dev.boostio.Events.InventoryClick;
import dev.boostio.Events.PlayerQuit;

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
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerPreLogin(), this.plugin);
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this.plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuit(), this.plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryClick(), this.plugin);
    }
}