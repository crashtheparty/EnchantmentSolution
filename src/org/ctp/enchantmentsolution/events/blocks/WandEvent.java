package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class WandEvent extends MultiBlockPlaceEvent {

	public WandEvent(List<Location> blocks, Player player, int level) {
		super(blocks, player, new EnchantmentLevel(CERegister.WAND, level));
	}

}
