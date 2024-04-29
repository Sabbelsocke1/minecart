package org.sabbelsocke.minecart.utils;

import org.bukkit.*;
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
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1.0f, 1.0f);
            }
            secondsRemaining--;
        }else {
            for (Player player1 : playerEntityMap.keySet()){
                player1.sendMessage( ChatColor.DARK_BLUE + "Das Rennen beginnt jetzt!");
                player1.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0f, 1.0f);
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
                    Vector direction = player.getLocation().getDirection();
                    direction.setY(0.0001);
                    direction.normalize();
                    double speed = 0.1;
                    minecart.setVelocity(direction.multiply(speed));
                }
            }

            this.cancel();
        }

        itemSpawnUtil.spawnRedstone(player);


    }
}
