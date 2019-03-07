package net.evatunasalkutil.salkcoding.event;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawn implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        if(event.getEntityType() == EntityType.VILLAGER){
            event.setCancelled(true);
        }
    }

}
