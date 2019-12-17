package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class MultiBlockPlaceEvent extends MultiBlockEvent {

	public MultiBlockPlaceEvent(Collection<Block> blocks, Player player, EnchantmentLevel enchantment) {
		super(blocks, player, enchantment);
	}

}
