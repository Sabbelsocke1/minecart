package org.sabbelsocke.minecart.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class itemSpawnUtil {

    static Player player;
    static Plugin plugin = (Plugin) Bukkit.getPluginManager().getPlugin("minecart");
    static YamlConfiguration config = (YamlConfiguration) plugin.getConfig();
    static Location location1;
    static Location location2;
    static Location location3;

    public static void spawnRedstone(Player p){
        player = p;
        location1 = new Location(player.getWorld(), (Double) config.get("itemSpawner1.x"), (Double) config.get("itemSpawner1.y"), (Double) config.get("itemSpawner1.z"));
        location2 = new Location(player.getWorld(), (Double) config.get("itemSpawner2.x"), (Double) config.get("itemSpawner2.y"), (Double) config.get("itemSpawner2.z"));
        location3 = new Location(player.getWorld(), (Double) config.get("itemSpawner3.x"), (Double) config.get("itemSpawner3.y"), (Double) config.get("itemSpawner3.z"));

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            ItemStack redstoneBlock = new ItemStack(Material.REDSTONE_BLOCK);
            ItemStack goldBlock = new ItemStack(Material.GOLD_BLOCK);

            int randomInt = (int) (Math.random() * 2);


            if (randomInt == 0) {

                spawnItem(location1, redstoneBlock);
                spawnItem(location2, redstoneBlock);
                spawnItem(location3, redstoneBlock);
            }else if (randomInt == 1){

                spawnItem(location1, goldBlock);
                spawnItem(location2, goldBlock);
                spawnItem(location3, goldBlock);
            }


        }, 0L, 20 * 20L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            task.cancel();
            removeItemsAtLocation(location1);
            removeItemsAtLocation(location2);
            removeItemsAtLocation(location3);
        }, 90 * 20L);
    }

    private static void spawnItem(Location location, ItemStack item) {
        for (Entity entity : location.getNearbyEntities(2, 3, 2)) {
            if (entity instanceof Item) {
                Item existingItem = (Item) entity;
                if (existingItem.getItemStack().getType() == item.getType()) {
                    return;
                }
            }
        }
        location.getWorld().dropItemNaturally(location, item);
    }

    private static void removeItemsAtLocation(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location, 1, 1, 1)) {
            if (entity instanceof Item) {
                entity.remove();
            }
        }
    }


}
