package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockEvent;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public abstract class BlockEffect extends EnchantmentEffect {

	private final BlockCondition[] conditions;

	public BlockEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	BlockCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Player player, BlockData brokenData, BlockEvent event) {
		for(BlockCondition condition: conditions)
			if (!condition.metCondition(player, brokenData, event)) return false;
		return true;
	}

}
