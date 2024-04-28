package org.sabbelsocke.minecart.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.sabbelsocke.minecart.listeners.playerInteractListener;

import java.util.Map;

public class countdownUtil extends BukkitRunnable {
    Plugin plugin = (Plugin) Bukkit.getPluginManager().getPlugin("minecart");

    private int secondsRemaining;
    private final Player player;

    public countdownUtil(Player player, int secondsRemaining) {
        this.player = player;
        this.secondsRemaining = secondsRemaining;
    }


    @Override
    public void run() {
        Map<Player, Entity> playerEntityMap = playerInteractListener.getPlayerEntityMap();
        if (secondsRemaining >0 ){
            for (Player player1 : playerEntityMap.keySet()){
                player1.sendMessage(ChatColor.YELLOW + "Das Rennen beginnt in " + secondsRemaining + "...");
            }
            secondsRemaining--;
        }else {
            for (Player player1 : playerEntityMap.keySet()){
                player1.sendMessage( ChatColor.DARK_BLUE + "Das Rennen beginnt jetzt!");
                Firework firework = player1.getWorld().spawn(player1.getLocation(), Firework.class);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();

                FireworkEffect effect = FireworkEffect.builder()
                        .with(FireworkEffect.Type.BALL)
                        .withColor(Color.AQUA)
                        .trail(true)
                        .flicker(true)
                        .build();

                fireworkMeta.addEffect(effect);
                fireworkMeta.setPower(1);

                firework.setFireworkMeta(fireworkMeta);
                Bukkit.getScheduler().runTaskLater(plugin, () -> firework.detonate(), 40);
            }



            for (Player player : playerEntityMap.keySet()) {
                Entity minecart = playerEntityMap.get(player);
                if (minecart.getType() == EntityType.MINECART && player.isInsideVehicle() && player.getVehicle().equals(minecart)) {
                    // Berechne die Bewegungsrichtung
                    Vector direction = player.getLocation().getDirection();
                    direction.setY(0.0001);
                    // Setze die Geschwindigkeit des Minecarts entsprechend der Spielerbewegung
                    double speed = 5; // Geschwindigkeit anpassen
                    minecart.setVelocity(direction.multiply(speed));
                }
            }

            this.cancel();
        }

    }
}
