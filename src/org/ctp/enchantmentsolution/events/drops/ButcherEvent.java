package org.ctp.enchantmentsolution.events.drops;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class ButcherEvent extends AddDropsEvent {

	public ButcherEvent(Player who, int level, List<ItemStack> drops) {
		super(who, new EnchantmentLevel(CERegister.BUTCHER, level), drops);
	}

}
