package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class MultiBlockPlaceEvent extends MultiBlockEvent {

	public MultiBlockPlaceEvent(List<Location> blocks, Player player, EnchantmentLevel enchantment) {
		super(blocks, player, enchantment);
	}

}
