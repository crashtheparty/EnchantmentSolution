package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class MagmaWalkerHotFloorEvent extends PreventCauseEvent {

	public MagmaWalkerHotFloorEvent(LivingEntity who, int level) {
		super(who, new EnchantmentLevel(CERegister.MAGMA_WALKER, level), DamageCause.HOT_FLOOR);
	}

}
