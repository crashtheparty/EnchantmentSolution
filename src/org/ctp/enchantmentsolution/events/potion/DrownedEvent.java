package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public class DrownedEvent extends CustomPotionAfflictEvent {

	public DrownedEvent(LivingEntity who, LivingEntity afflicter, int level, CustomPotionEffectType type, int duration,
	boolean override) {
		super(who, afflicter, new EnchantmentLevel(CERegister.DROWNED, level), type, duration, 0, override);
	}

}
