package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class MagicGuardCauseEvent extends PreventCauseEvent {

	public MagicGuardCauseEvent(LivingEntity who, int level, DamageCause cause) {
		super(who, new EnchantmentLevel(CERegister.MAGIC_GUARD, level), cause);
	}

}
