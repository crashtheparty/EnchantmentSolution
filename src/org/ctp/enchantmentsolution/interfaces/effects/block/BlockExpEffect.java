package org.ctp.enchantmentsolution.interfaces.effects.block;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.interfaces.effects.BlockEffect;

public abstract class BlockExpEffect extends BlockEffect {

	private final BlockCondition[] conditions;

	public BlockExpEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	BlockCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Player player, BlockData brokenData, BlockExpEvent event) {
		for(BlockCondition condition: conditions)
			if (!condition.metCondition(player, brokenData, event)) return false;
		return true;
	}

	public abstract EffectResult run(Player player, ItemStack[] items, BlockData brokenData, BlockExpEvent event);

}
