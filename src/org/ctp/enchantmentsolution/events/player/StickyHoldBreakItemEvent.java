package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class StickyHoldBreakItemEvent extends ESItemBreakEvent {

	public StickyHoldBreakItemEvent(Player who, int level, ItemStack item) {
		super(who, new EnchantmentLevel(CERegister.STICKY_HOLD, level), item);
	}

}
