package org.ctp.enchantmentsolution.events.fishing;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class FriedEvent extends FishingEvent {

	public FriedEvent(Player who, ItemStack drop, ItemStack originalItem, boolean override, int exp) {
		super(who, new EnchantmentLevel(CERegister.FRIED, 1), drop, originalItem, override, exp);
	}

}
