package net.evatunasalkutil.salkcoding.event;

import net.evatunasalkutil.salkcoding.Constants;
import net.evatunasalkutil.salkcoding.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;

public class PlayerTeleport implements Listener {

    private HashMap<String, CoolTimer> map = new HashMap<>();

    private HashSet<String> playerSet = new HashSet<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isOp())
            return;
        String name = event.getPlayer().getName();
        playerSet.add(name);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (event.getPlayer().isOp())
            return;
        String name = event.getPlayer().getName();
        playerSet.remove(name);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN || event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
            Player player = event.getPlayer();
            if (player.isOp())
                return;
            if (playerSet.contains(player.getName())) {
                playerSet.remove(player.getName());
                event.setCancelled(true);
                return;
            }
            if (map.containsKey(player.getName())) {
                CoolTimer timer = map.get(player.getName());
                if (timer.getCount() <= 0) {
                    map.remove(player.getName());
                    return;
                } else
                    timer.stop();
            }
            player.closeInventory();
            event.setCancelled(true);
            CoolTimer timer = new CoolTimer(player, event.getTo());
            timer.setTask(Bukkit.getScheduler().runTaskTimer(Main.getInstance(), timer, 0, 1));
            map.put(player.getName(), timer);
        }
    }

}

class CoolTimer implements Runnable {

    private BukkitTask task;
    private int count = 5 * 20;
    private Player player;
    private Location to;

    CoolTimer(Player player, Location to) {
        this.player = player;
        this.to = to;
    }

    private Location last;

    @Override
    public void run() {
        if (count < 0) {
            player.teleport(to);
            task.cancel();
            return;
        } else
            player.sendTitle(ChatColor.GOLD + "텔레포트 중입니다...", ChatColor.GRAY + "이동 " + String.format("%.1f", (count / 20f)) + "초 전...", 0, 20, 10);
        if (last != null) {
            if(!last.getWorld().getName().equals(player.getLocation().getWorld().getName())) {
                task.cancel();
                return;
            }
            if (last.distance(player.getLocation()) > 1) {
                player.sendMessage(Constants.Warn_Format + "텔레포트 중 이동하셔서 텔레포트가 취소됩니다.");
                task.cancel();
                return;
            }
        } else last = player.getLocation();
        count--;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public int getCount() {
        return count;
    }

    public void stop() {
        count = -1;
    }

}
