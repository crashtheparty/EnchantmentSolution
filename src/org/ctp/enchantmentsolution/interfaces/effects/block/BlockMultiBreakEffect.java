package org.ctp.enchantmentsolution.interfaces.effects.block;

import org.bukkit.event.EventPriority;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public abstract class BlockMultiBreakEffect extends BlockBreakEffect {

	public BlockMultiBreakEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	BlockCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

}
