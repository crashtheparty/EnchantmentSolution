package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class HollowPointDamageEvent extends EntityDamageByEntityEvent {

	private final EnchantmentLevel enchantment;

	public HollowPointDamageEvent(Entity damager, Entity damagee, DamageCause cause, double damage) {
		super(damager, damagee, cause, damage);
		enchantment = new EnchantmentLevel(CERegister.HOLLOW_POINT, 1);
	}

	public EnchantmentLevel getEnchantment() {
		return enchantment;
	}

}
