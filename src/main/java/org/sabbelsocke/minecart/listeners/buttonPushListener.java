package org.sabbelsocke.minecart.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.sabbelsocke.minecart.utils.countdownUtil;

import java.util.HashMap;
import java.util.Map;


public class buttonPushListener implements Listener {

    Map<Player, Entity> playerEntityMap = new HashMap<>();

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event){
        setPlayerEntityMap();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();

            Plugin plugin = (Plugin) Bukkit.getPluginManager().getPlugin("minecart");
            YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

            int expectedX = config.getInt("startbutton.x");
            int expectedY = config.getInt("startbutton.y");
            int expectedZ = config.getInt("startbutton.z");

            if (clickedBlock != null &&
                    clickedBlock.getX() == expectedX &&
                    clickedBlock.getY() == expectedY &&
                    clickedBlock.getZ() == expectedZ
            ){


                if (playerInteractListener.getPlayerEntityMap().size() == 0) {
                    Location location = new Location(player.getWorld(), config.getInt("start1.x"), config.getInt("start1.y"), config.getInt("start1.z"));
                    spawnMinecart(player, location);

                } else if (playerInteractListener.getPlayerEntityMap().size() == 1) {
                    Location location = new Location(player.getWorld(), config.getInt("start2.x"), config.getInt("start2.y"), config.getInt("start2.z"));
                    spawnMinecart(player, location);

                    countdownUtil countdownUtil = new countdownUtil( player, 5);
                    countdownUtil.runTaskTimer(plugin, 0, 20);

                }else if (playerInteractListener.getPlayerEntityMap().size() == 2) {
                    Location location = new Location(player.getWorld(), config.getInt("start3.x"), config.getInt("start3.y"), config.getInt("start3.z"));
                    spawnMinecart(player, location);

                }else if (playerInteractListener.getPlayerEntityMap().size() == 3) {
                    Location location = new Location(player.getWorld(), config.getInt("start4.x"), config.getInt("start4.y"), config.getInt("start4.z"));
                    spawnMinecart(player, location);


                }
            }
        }

    }

    public void spawnMinecart(Player player, Location location){
        Entity minecart = player.getWorld().spawnEntity(location, EntityType.MINECART);
        minecart.setPersistent(true);
        minecart.addPassenger(player);
        minecart.setGravity(false);
        float currentYaw = minecart.getLocation().getYaw();
        float newYaw = currentYaw - 90;
        minecart.setRotation(newYaw, minecart.getLocation().getPitch());

        playerEntityMap.put(player, minecart);
        playerInteractListener.setPlayerEntityMap(playerEntityMap);

    }

    public void setPlayerEntityMap(){
        playerEntityMap = playerInteractListener.getPlayerEntityMap();
    }
}
