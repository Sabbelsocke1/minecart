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

public class setStartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        Plugin plugin = (Plugin) Bukkit.getPluginManager().getPlugin("minecart");
        YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

        if (sender instanceof Player){

            Location location = ((Player) sender).getLocation();
            if (args[0].equals("1")){
                config.set("start1.x", location.getX());
                config.set("start1.y", location.getY());
                config.set("start1.z", location.getZ());
                sender.sendMessage("Die Startposition 1 wurde erfolgreich gesetzt!");
                plugin.saveConfig();
                plugin.reloadConfig();
            } else if (args[0].equals("2")) {
                config.set("start2.x", location.getX());
                config.set("start2.y", location.getY());
                config.set("start2.z", location.getZ());
                sender.sendMessage("Die Startposition 2 wurde erfolgreich gesetzt!");
                plugin.saveConfig();
                plugin.reloadConfig();
            }else if (args[0].equals("3")) {
                config.set("start3.x", location.getX());
                config.set("start3.y", location.getY());
                config.set("start3.z", location.getZ());
                sender.sendMessage("Die Startposition 3 wurde erfolgreich gesetzt!");
                plugin.saveConfig();
                plugin.reloadConfig();
            }else if (args[0].equals("4")) {
                config.set("start4.x", location.getX());
                config.set("start4.y", location.getY());
                config.set("start4.z", location.getZ());
                sender.sendMessage("Die Startposition 4 wurde erfolgreich gesetzt!");
                plugin.saveConfig();
                plugin.reloadConfig();
            }
        }
        return true;
    }
}
