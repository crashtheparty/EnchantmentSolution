package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public class SandVeilEvent extends CustomPotionAfflictEvent {

	public SandVeilEvent(LivingEntity who, LivingEntity afflicter, int level, CustomPotionEffectType type, int duration, int potLevel,
	boolean override) {
		super(who, afflicter, new EnchantmentLevel(CERegister.SAND_VEIL, level), type, duration, potLevel, override);
	}

}
