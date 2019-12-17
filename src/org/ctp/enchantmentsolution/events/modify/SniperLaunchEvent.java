package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class SniperLaunchEvent extends ModifySpeedEvent {

	public SniperLaunchEvent(Player who, int level, double speed) {
		super(who, new EnchantmentLevel(CERegister.SNIPER, level), speed);
	}

}
