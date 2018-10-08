package org.ctp.enchantmentsolution.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.EnchantmentTable;
import org.ctp.enchantmentsolution.inventory.InventoryData;

public class PlayerInteract implements Listener{
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
		        return; // off hand packet, ignore.
		    }
			Block block = event.getClickedBlock();
			if(block.getType().equals(Material.ENCHANTING_TABLE)){
				Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.PLUGIN, new Runnable() {
					public void run() {
						if(event.isCancelled()) return;
						Player player = event.getPlayer();
						InventoryData inv = EnchantmentSolution.getInventory(player);
						if(inv == null) {
							inv = new EnchantmentTable(player, block);
							EnchantmentSolution.addInventory(inv);
						} else if (!(inv instanceof EnchantmentTable)) {
							inv.close(true);
							inv = new EnchantmentTable(player, block);
							EnchantmentSolution.addInventory(inv);
						}
						inv.setInventory(null);
					}
					
				}, 1l);
			}
			if(block.getType().equals(Material.ANVIL) || block.getType().equals(Material.CHIPPED_ANVIL) || block.getType().equals(Material.DAMAGED_ANVIL)){
				Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.PLUGIN, new Runnable() {
					public void run() {
						if(event.isCancelled()) return;
						Player player = event.getPlayer();
						InventoryData inv = EnchantmentSolution.getInventory(player);
						if(inv == null || !(inv instanceof Anvil)) {
							inv = new Anvil(player, block);
							EnchantmentSolution.addInventory(inv);
						} else if (!(inv instanceof Anvil)) {
							inv.close(true);
							inv = new Anvil(player, block);
							EnchantmentSolution.addInventory(inv);
						}
						inv.setInventory(null);
					}
					
				}, 1l);
			}
		}
	}

}
