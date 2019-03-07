package net.evatunasalkutil.salkcoding.event;

import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PotionSplashEvent;

public class EntityPotion implements Listener {

    @EventHandler
    public void onPotion(PotionSplashEvent event) {
        for (Entity entity : event.getAffectedEntities()) {
            if (entity.getType() == EntityType.ZOMBIE_VILLAGER) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPotion(LingeringPotionSplashEvent event) {
        AreaEffectCloud potion = event.getAreaEffectCloud();
        for (Entity entity : potion.getNearbyEntities(potion.getRadius(), potion.getRadius(), potion.getRadius())) {
            if (entity.getType() == EntityType.ZOMBIE_VILLAGER) {
                event.setCancelled(true);
            }
        }
    }

}
