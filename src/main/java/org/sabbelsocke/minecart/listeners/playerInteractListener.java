package org.sabbelsocke.minecart.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


import java.util.HashMap;
import java.util.Map;

public class playerInteractListener implements Listener {


    static Map<Player, Entity> playerEntityMap = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        // Bedingung ist das Spieler ein Minecart in der Hand Hölt
        if (player.getItemInHand().getType().equals(Material.MINECART)){

            Entity minecart = player.getWorld().spawnEntity(player.getLocation(), EntityType.MINECART);
            minecart.setPersistent(true);
            minecart.addPassenger(player);
            minecart.setGravity(false);

            playerEntityMap.put(player, minecart);

        }
    }

    public static Map<Player, Entity> getPlayerEntityMap() {
        return playerEntityMap;
    }

    public static void setPlayerEntityMap(Map<Player, Entity> map) {
        playerInteractListener.playerEntityMap = map;
    }
}
