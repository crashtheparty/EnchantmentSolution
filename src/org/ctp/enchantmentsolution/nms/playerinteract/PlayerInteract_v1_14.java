package org.ctp.enchantmentsolution.nms.playerinteract;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Grindstone;
import org.ctp.enchantmentsolution.inventory.InventoryData;

public class PlayerInteract_v1_14 {

	public static void onPlayerInteract(PlayerInteractEvent event){
		Block block = event.getClickedBlock();
		if(block.getType().equals(Material.GRINDSTONE)){
			Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), new Runnable() {
				public void run() {
					if(event.isCancelled()) return;
					Player player = event.getPlayer();
					InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
					if(inv == null) {
						inv = new Grindstone(player, block);
						EnchantmentSolution.getPlugin().addInventory(inv);
					} else if (!(inv instanceof Grindstone)) {
						inv.close(true);
						inv = new Grindstone(player, block);
						EnchantmentSolution.getPlugin().addInventory(inv);
					}
					inv.setInventory(null);
				}
				
			}, 1l);
		}
	}
	
}
