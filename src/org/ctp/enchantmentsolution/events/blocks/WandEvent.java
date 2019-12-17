package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class WandEvent extends MultiBlockPlaceEvent {

	public WandEvent(Collection<Block> blocks, Player player, int level) {
		super(blocks, player, new EnchantmentLevel(CERegister.WAND, level));
	}

}
