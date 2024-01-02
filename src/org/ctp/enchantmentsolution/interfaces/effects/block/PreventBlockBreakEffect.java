package org.ctp.enchantmentsolution.interfaces.effects.block;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public abstract class PreventBlockBreakEffect extends BlockBreakEffect {

	public PreventBlockBreakEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	BlockCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

	@Override
	public PreventBlockBreakResult run(Player player, ItemStack[] items, BlockData brokenData, BlockBreakEvent event) {
		int level = getLevel(items);
		
		return new PreventBlockBreakResult(level);
	}

	public class PreventBlockBreakResult extends EffectResult {

		public PreventBlockBreakResult(int level) {
			super(level);
		}
	}

}
