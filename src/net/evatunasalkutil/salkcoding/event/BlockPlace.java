package net.evatunasalkutil.salkcoding.event;

import net.evatunasalkutil.salkcoding.Constants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.SIGN) {
            Location location = block.getLocation();
            for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++)
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    Block b = location.getWorld().getBlockAt(x, location.getBlockY(), z);
                    if (b.getType() == Material.CHEST) {
                        Player player = event.getPlayer();
                        player.sendMessage(Constants.Error_Format + "상자 주위에는 표지판을 설치 할 수 없습니다.");
                        player.closeInventory();
                        event.setCancelled(true);
                    }
                }
        }
    }

}