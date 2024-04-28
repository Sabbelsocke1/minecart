package org.sabbelsocke.minecart.listeners;

import com.google.gson.JsonObject;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.*;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import javax.swing.border.TitledBorder;
import java.util.Map;

public class minecartMoveListener implements Listener {

    @EventHandler
    public void onVehicleMoveEvent(VehicleMoveEvent event){

        Map<Player, Entity> playerEntityMap = playerInteractListener.getPlayerEntityMap();
        if (event.getVehicle() instanceof Minecart){
            Entity minecart = event.getVehicle();


            //Bukkit.broadcastMessage("Pass");
                if(!event.getVehicle().getPassengers().isEmpty()){
                    //Bukkit.broadcastMessage("Pass2");
                    Player player = (Player) event.getVehicle().getPassenger();

                if (playerEntityMap.get(player).equals(minecart)){
                    //Bukkit.broadcastMessage("Pass3");

                    Location playerEyeLocation = player.getEyeLocation();
                    Vector playerDirection = playerEyeLocation.getDirection();

                    playerDirection.setY(0);
                    playerDirection.normalize();

                    double speed = 5;

                    minecart.setVelocity(playerDirection.multiply(speed));

                }
                }
        }

        Plugin plugin = (Plugin) Bukkit.getPluginManager().getPlugin("minecart");
        YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

        int fx1 = config.getInt("finishp1.x");
        int fy1 = config.getInt("finishp1.y");
        int fz1 = config.getInt("finishp1.z");
        int fx2 = config.getInt("finishp2.x");
        int fy2 = config.getInt("finishp2.y");
        int fz2 = config.getInt("finishp2.z");

        Player player = (Player) event.getVehicle().getPassenger();
        Location playerPos = player.getLocation();

        boolean crossFinish = playerPos.getBlockX() >= Math.min(fx1, fx2) &&
                playerPos.getBlockX() <= Math.max(fx1, fx2) &&
                playerPos.getBlockY() >= Math.min(fy1, fy2) &&
                playerPos.getBlockY() <= Math.max(fy1, fy2) &&
                playerPos.getBlockZ() >= Math.min(fz1, fz2) &&
                playerPos.getBlockZ() <= Math.max(fz1, fz2);

        if (crossFinish) {
            for (Player player1 : playerEntityMap.keySet()){
                player1.sendTitle("", ChatColor.GREEN + player.getName() + " hat gewonnen!");
                player1.leaveVehicle();
                Location location = new Location(player1.getWorld(), config.getInt("Lobby.x"), config.getInt("Lobby.y"), config.getInt("Lobby.z"));
                player1.teleport(location);
            }

        }







    }
}
