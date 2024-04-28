package org.sabbelsocke.minecart;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.sabbelsocke.minecart.commands.*;
import org.sabbelsocke.minecart.listeners.buttonPushListener;
import org.sabbelsocke.minecart.listeners.minecartMoveListener;
import org.sabbelsocke.minecart.listeners.playerInteractListener;
import org.sabbelsocke.minecart.listeners.vehicleExitListener;

import java.io.File;
import java.io.IOException;

public final class Minecart extends JavaPlugin {

    private File configFile;
    private YamlConfiguration config;
    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new minecartMoveListener(), this);
        pluginManager.registerEvents(new playerInteractListener(), this);
        pluginManager.registerEvents(new vehicleExitListener(), this);
        pluginManager.registerEvents(new buttonPushListener(), this);
        getCommand("start").setExecutor(new startCommand());
        getCommand("setfinish").setExecutor(new finishCommand());
        getCommand("setlobby").setExecutor(new setLobbyCommand());
        getCommand("setstart").setExecutor(new setStartCommand());
        getCommand("setstartbutton").setExecutor(new setButtonCommand());

        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()){
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);


    }

    @Override
    public void onDisable() {

        try {
            config.save(configFile);
        }catch (IOException e){
            getLogger().warning("Fehler beim Speichern der Config");
        }
        // Plugin shutdown logic
    }

    @NotNull
    public YamlConfiguration getConfig() {
        return config;
    }

    public void reloadPluginConfig() {
        reloadConfig();
        config = getConfig();
    }



}
