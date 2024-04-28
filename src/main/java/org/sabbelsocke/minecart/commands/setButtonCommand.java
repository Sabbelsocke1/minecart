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

public class setButtonCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Plugin plugin = (Plugin) Bukkit.getPluginManager().getPlugin("minecart");
        YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

        if (sender instanceof Player){
            Location location = ((Player) sender).getLocation();

            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            config.set("startbutton.x", x);
            config.set("startbutton.y", y);
            config.set("startbutton.z", z);

            sender.sendMessage("Die Buttonposition wurde erfolgreich gesetzt!");
            plugin.saveConfig();
            plugin.reloadConfig();
        }

        return true;
    }
}
