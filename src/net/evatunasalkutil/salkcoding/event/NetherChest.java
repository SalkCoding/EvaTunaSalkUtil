package net.evatunasalkutil.salkcoding.event;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class NetherChest implements Listener {

    @EventHandler
    public void onInventoryPickUp(InventoryMoveItemEvent event) {
        Inventory source = event.getSource();
        Inventory destination = event.getDestination();
        if (event.getSource().getLocation().getWorld().getEnvironment() == World.Environment.NETHER) {
            if (source.getType() == InventoryType.CHEST || source.getType() == InventoryType.HOPPER || destination.getType() == InventoryType.HOPPER /*|| destination.getType() == InventoryType.CHEST*/) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        if(block.getWorld().getEnvironment() == World.Environment.NETHER){
            if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
                block.setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event){
        Block block = event.getBlock();
        if(block.getWorld().getEnvironment() == World.Environment.NETHER){
            if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
                block.setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onCheckClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world.getEnvironment() == World.Environment.NETHER) {
            Block clickedChest = event.getClickedBlock();
            if(clickedChest == null)
                return;
            if (clickedChest.getType() == Material.CHEST || clickedChest.getType() == Material.TRAPPED_CHEST) {
                Chest chest = (Chest) clickedChest.getState();
                chest.getBlockInventory().clear();
                clickedChest.setType(Material.AIR);
                event.setCancelled(true);
                return;
            }
        }
    }

}
