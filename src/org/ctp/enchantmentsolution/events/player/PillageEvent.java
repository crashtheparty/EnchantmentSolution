package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class PillageEvent extends ESPlayerEvent {

	public PillageEvent(Player who, int level) {
		super(who, new EnchantmentLevel(CERegister.PILLAGE, level));
	}

}
