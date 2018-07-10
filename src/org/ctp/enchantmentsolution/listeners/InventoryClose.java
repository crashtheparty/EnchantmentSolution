package org.ctp.enchantmentsolution.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.EnchantmentTable;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class InventoryClose implements Listener{

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		Player player = (Player) event.getPlayer();
		EnchantmentTable removeTable = null;
		for(EnchantmentTable table : EnchantmentSolution.TABLES){
			if(table.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())){
				if(!table.openingNew()){
					removeTable = table;
					break;
				}
			}
		}
		if(removeTable != null){
			for(ItemStack item : removeTable.getItems()){
				ItemUtils.giveItemToPlayer(player, item, player.getLocation());
			}
			EnchantmentSolution.TABLES.remove(removeTable);
		}
		Anvil removeAnvil = null;
		for(Anvil anvil : EnchantmentSolution.ANVILS){
			if(anvil.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())){
				if(!anvil.openingNew()){
					removeAnvil = anvil;
					break;
				}
			}
		}
		if(removeAnvil != null){
			if(!removeAnvil.isRenaming()) {
				removeAnvil.close(true);
			}
		}
	}
	
}
