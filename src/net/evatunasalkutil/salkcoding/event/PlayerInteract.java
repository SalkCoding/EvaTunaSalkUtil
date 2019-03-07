package net.evatunasalkutil.salkcoding.event;

import net.evatunasalkutil.salkcoding.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class PlayerInteract implements Listener {

    HashMap<String, BukkitTask> playerSet = new HashMap<>();

    /*@EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            Player player = event.getPlayer();
            Player target = (Player) event.getRightClicked();
            if (player.isSneaking() && !playerSet.containsKey(player.getName())) {
                playerSet.put(player.getName(), Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    //Bukkit.dispatchCommand(player, "trade " + target.getName());
                    playerSet.get(player.getName()).cancel();
                    playerSet.remove(player.getName());
                }, 20));
            }
        }
    }*/

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] command = event.getMessage().split(" ");
        if (command[0].equalsIgnoreCase("/trade")) {
            if (!event.getPlayer().isOp())
                event.setCancelled(true);
        }
    }

}
