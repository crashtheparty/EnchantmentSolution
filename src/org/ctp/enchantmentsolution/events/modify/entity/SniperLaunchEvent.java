package org.ctp.enchantmentsolution.events.modify.entity;

import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class SniperLaunchEvent extends ModifyEntitySpeedEvent {

	public SniperLaunchEvent(Entity who, int level, double speedX, double speedY, double speedZ) {
		super(who, new EnchantmentLevel(CERegister.SNIPER, level), speedX, speedY, speedZ);
	}

}
