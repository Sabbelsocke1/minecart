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
import org.sabbelsocke.minecart.Minecart;

public class finishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Plugin plugin = Bukkit.getPluginManager().getPlugin("minecart");
        YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

        if (args.length > 0) {
            if (args[0].equals("1")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    Location location = player.getLocation();

                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();

                    config.set("finishp1.x", x);
                    config.set("finishp1.y", y);
                    config.set("finishp1.z", z);

                    player.sendMessage("Neuer Zielpunkt 1 auf " + x + " " + y + " " + z + " gesetzt!");
                    plugin.saveConfig();
                    plugin.reloadConfig();
                }
            } else if (args[0].equals("2")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    Location location = player.getLocation();

                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();

                    config.set("finishp2.x", x);
                    config.set("finishp2.y", y);
                    config.set("finishp2.z", z);

                    player.sendMessage("Neuer Zielpunkt 2 auf " + x + " " + y + " " + z + " gesetzt!");
                    plugin.saveConfig();
                    plugin.reloadConfig();
                }
            }
        }

        return true;
    }

}
