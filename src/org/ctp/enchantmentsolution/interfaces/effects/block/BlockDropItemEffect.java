package org.ctp.enchantmentsolution.interfaces.effects.block;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public abstract class BlockDropItemEffect extends EnchantmentEffect {

	private final BlockCondition[] conditions;

	public BlockDropItemEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	BlockCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Player player, BlockData brokenData, BlockDropItemEvent event) {
		for(BlockCondition condition: conditions)
			if (!condition.metCondition(player, brokenData, event)) return false;
		return true;
	}

	public abstract EffectResult run(Player player, ItemStack[] items, BlockData brokenData, BlockDropItemEvent event);

}