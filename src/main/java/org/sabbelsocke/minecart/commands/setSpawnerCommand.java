package org.sabbelsocke.minecart.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class setSpawnerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        Plugin plugin = (Plugin) Bukkit.getPluginManager().getPlugin("minecart");
        YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

        if (sender instanceof Player){

            Location location = ((Player) sender).getLocation();
            if (args[0].equals("1")){
                config.set("itemSpawner1.x", location.getX());
                config.set("itemSpawner1.y", location.getY());
                config.set("itemSpawner1.z", location.getZ());
                sender.sendMessage("Die Spawnerposition 1 wurde erfolgreich gesetzt!");
                plugin.saveConfig();
                plugin.reloadConfig();
            } else if (args[0].equals("2")) {
                config.set("itemSpawner2.x", location.getX());
                config.set("itemSpawner2.y", location.getY());
                config.set("itemSpawner2.z", location.getZ());
                sender.sendMessage("Die Spawnerposition 2 wurde erfolgreich gesetzt!");
                plugin.saveConfig();
                plugin.reloadConfig();
            }else if (args[0].equals("3")) {
                config.set("itemSpawner3.x", location.getX());
                config.set("itemSpawner3.y", location.getY());
                config.set("itemSpawner3.z", location.getZ());
                sender.sendMessage("Die Spawnerposition 3 wurde erfolgreich gesetzt!");
                plugin.saveConfig();
                plugin.reloadConfig();
            }
        }


        return true;
    }
}
