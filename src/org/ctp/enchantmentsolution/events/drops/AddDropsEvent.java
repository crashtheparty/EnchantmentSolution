package org.ctp.enchantmentsolution.events.drops;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class AddDropsEvent extends DropEvent {

	public AddDropsEvent(Player who, EnchantmentLevel enchantment, List<ItemStack> drops) {
		super(who, enchantment, drops);
	}

}
