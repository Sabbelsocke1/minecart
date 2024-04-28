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

public class setLobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Plugin plugin = Bukkit.getPluginManager().getPlugin("minecart");
        YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation();

            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            config.set("Lobby.x", x);
            config.set("Lobby.y", y);
            config.set("Lobby.z", z);

            player.sendMessage("Neuer Lobbypunkt auf " + x + " " + y + " " + z + " gesetzt!");
            plugin.saveConfig();
            plugin.reloadConfig();
        }

        return true;
    }
}
