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
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.EnchantmentTable;

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
						EnchantmentTable table = null;
						int books = Enchantments.getBookshelves(block.getLocation());
						for (EnchantmentTable t : EnchantmentSolution.TABLES) {
							if (t.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString()) && t.getBooks() == books) {
								table = t;
							}
						}
						if (table == null) {
							table = new EnchantmentTable(player, books);
							EnchantmentSolution.TABLES.add(table);
						}
						table.setInventory(null);
					}
					
				}, 1l);
			}
			if(block.getType().equals(Material.ANVIL)){
				Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.PLUGIN, new Runnable() {
					public void run() {
						if(event.isCancelled()) return;
						Player player = event.getPlayer();
						Anvil anvil = null;
						for (Anvil a : EnchantmentSolution.ANVILS) {
							if (a.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) {
								anvil = a;
							}
						}
						if (anvil == null) {
							anvil = new Anvil(player, block);
							EnchantmentSolution.ANVILS.add(anvil);
						}
						anvil.setInventory(null);
					}
					
				}, 1l);
			}
		}
	}

}
