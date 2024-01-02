package org.ctp.enchantmentsolution.events.modify.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class HardBounceEvent extends ModifyPlayerSpeedEvent {

	public HardBounceEvent(Player who, int level, double speed) {
		super(who, new EnchantmentLevel(CERegister.HARD_BOUNCE, level), speed);
	}

}
