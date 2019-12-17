package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ModifyBlockEvent extends ESPlayerEvent {

	public ModifyBlockEvent(Player who, EnchantmentLevel enchantment) {
		super(who, enchantment);
	}
}
