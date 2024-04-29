package org.sabbelsocke.minecart.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class minecartMoveListener implements Listener {
    double speed = 5;
    private ConcurrentHashMap<Player, Integer> crossedLinesMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Player, Boolean> isCrossedMap = new ConcurrentHashMap<>();

    boolean isCrossed = false;

    @EventHandler
    public void onVehicleMoveEvent(VehicleMoveEvent event) {
        Map<Player, Entity> playerEntityMap = playerInteractListener.getPlayerEntityMap();
        if (event.getVehicle() instanceof Minecart) {
            Entity minecart = event.getVehicle();
            if (!event.getVehicle().getPassengers().isEmpty()) {
                Player player = (Player) event.getVehicle().getPassenger();
                if (playerEntityMap.get(player).equals(minecart)) {
                    Location playerEyeLocation = player.getEyeLocation();
                    Vector playerDirection = playerEyeLocation.getDirection();
                    playerDirection.setY(0);
                    playerDirection.normalize();
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

        boolean isCrossed = isCrossedMap.getOrDefault(player, false);
        if (crossFinish &&!isCrossed) {
            crossedLinesMap.compute(player, (k, v) -> v == null? 1 : v + 1);
            isCrossedMap.put(player, true);
            int crossedlines = crossedLinesMap.get(player);
            if (crossedlines < 4) {
                player.sendTitle("", ChatColor.DARK_AQUA +"Runde " +  crossedlines + "/3!");
            } else {
                Iterator<Map.Entry<Player, Integer>> iterator = crossedLinesMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Player, Integer> entry = iterator.next();
                    Player player1 = entry.getKey();
                    player1.sendTitle("", ChatColor.GREEN + player.getName() + " hat gewonnen!");
                    player1.leaveVehicle();
                    Location location = new Location(player1.getWorld(), config.getInt("Lobby.x"), config.getInt("Lobby.y"), config.getInt("Lobby.z"));
                    player1.teleport(location);
                    iterator.remove(); // Remove the entry from the map
                }
                isCrossedMap.put(player, false);
            }
        } else if (!crossFinish && isCrossed) {
            isCrossedMap.put(player, false);
        }
    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @EventHandler
    public void onItemPickupEvent(PlayerPickupItemEvent event) {
        Map<Player, Entity> playerEntityMap = playerInteractListener.getPlayerEntityMap();
        Plugin plugin = Bukkit.getPluginManager().getPlugin("minecart");

        if (event.getPlayer().getVehicle()!= null && event.getPlayer().getVehicle() instanceof Minecart) {
            Player player = event.getPlayer();
            if (playerEntityMap.get(player).equals(player.getVehicle())) {
                if (event.getItem().getItemStack().getType() == Material.REDSTONE_BLOCK) {
                    Minecart minecart = (Minecart) player.getVehicle();
                    Vector direction = player.getLocation().getDirection();
                    direction.normalize();
                    double speed = 0;
                    minecart.setVelocity(direction.multiply(speed));

                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        player.getInventory().remove(Material.REDSTONE_BLOCK);
                        Vector direction2 = player.getLocation().getDirection();
                        direction2.setY(0.0001);
                        direction2.normalize();
                        // Setze die Geschwindigkeit des Minecarts entsprechend der Spielerbewegung
                        double speed2 = 0.1; // Geschwindigkeit anpassen
                        minecart.setVelocity(direction2.multiply(speed2));

                    }, 20L);
                }
            }
        }
    }

    public void resetCrossedLines(Player player) {
        crossedLinesMap.remove(player);
    }
}