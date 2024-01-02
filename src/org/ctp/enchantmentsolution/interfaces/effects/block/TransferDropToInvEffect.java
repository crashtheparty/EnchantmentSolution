package org.ctp.enchantmentsolution.interfaces.effects.block;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public abstract class TransferDropToInvEffect extends BlockDropItemEffect {

	public TransferDropToInvEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	BlockCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

	@Override
	public TransferDropToInvResult run(Player player, ItemStack[] items, BlockData brokenData, BlockDropItemEvent event) {
		int level = getLevel(items);

		return new TransferDropToInvResult(level);
	}

	public class TransferDropToInvResult extends EffectResult {

		public TransferDropToInvResult(int level) {
			super(level);
		}
	}

}
