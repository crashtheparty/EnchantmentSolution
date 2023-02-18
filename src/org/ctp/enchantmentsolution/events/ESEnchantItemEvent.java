package org.ctp.enchantmentsolution.events;

import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class ESEnchantItemEvent extends EnchantItemEvent {

	public ESEnchantItemEvent(Player enchanter, InventoryView view, Block table, ItemStack item, int level, Map<Enchantment, Integer> enchants, int i) {
		super(enchanter, view, table, item, level, enchants, i);
	}

}
