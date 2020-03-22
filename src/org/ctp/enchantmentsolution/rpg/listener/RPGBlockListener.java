package org.ctp.enchantmentsolution.rpg.listener;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class RPGBlockListener extends Enchantmentable {

	@EventHandler(priority=EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if(item != null && ItemUtils.hasEnchantment(item, Enchantment.SILK_TOUCH) && ItemBreakType.getType(item.getType()).getSilkBreakTypes().contains(event.getBlock().getType())) RPGUtils.giveExperience(player, Enchantment.SILK_TOUCH, event.getBlock().getType());
	}
	
}
