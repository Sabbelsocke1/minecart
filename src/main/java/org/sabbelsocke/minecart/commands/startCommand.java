package org.sabbelsocke.minecart.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.sabbelsocke.minecart.listeners.minecartMoveListener;
import org.sabbelsocke.minecart.listeners.playerInteractListener;
import org.sabbelsocke.minecart.utils.countdownUtil;

import java.util.Map;

public class startCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        countdownUtil countdownUtil = new countdownUtil((Player) commandSender, 5);
        Plugin plugin = (Plugin) Bukkit.getPluginManager().getPlugin("minecart");
        countdownUtil.runTaskTimer(plugin, 0, 20);


        return true;
    }
}
