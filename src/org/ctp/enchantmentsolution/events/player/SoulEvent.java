package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class SoulEvent extends ESPlayerEvent {

	public SoulEvent(Player who, EnchantmentLevel enchantment) {
		super(who, enchantment);
	}

}
