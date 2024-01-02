package org.ctp.enchantmentsolution.events.fishing;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class AnglerEvent extends FishingEvent {

	public AnglerEvent(Player who, int level, ItemStack drop, ItemStack originalItem, boolean override, int exp) {
		super(who, new EnchantmentLevel(CERegister.ANGLER, level), drop, originalItem, override, exp);
	}

}
