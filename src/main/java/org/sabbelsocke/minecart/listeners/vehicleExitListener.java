package org.sabbelsocke.minecart.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class vehicleExitListener implements Listener {

    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {
        Map<Player, Entity> playerEntityMap = playerInteractListener.getPlayerEntityMap();
        Plugin plugin = (Plugin) Bukkit.getPluginManager().getPlugin("minecart");
        YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

        if (event.getVehicle() instanceof Minecart) {
            Player player = (Player) event.getExited();

            if (playerEntityMap.containsKey(player) && playerEntityMap.get(player).equals(event.getVehicle())) {
                event.getVehicle().remove();
                playerEntityMap.remove(player);
                playerInteractListener.setPlayerEntityMap(playerEntityMap);
                Location location = new Location(player.getWorld(), config.getInt("Lobby.x"), config.getInt("Lobby.y"), config.getInt("Lobby.z"));
                player.teleport(location);
            }
        }
    }
}