package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.utils.abilityhelpers.Streak;
import org.ctp.enchantmentsolution.utils.player.ESEntity;

public class StreakTypeCondition implements DamageCondition {
	
	private final String field;
	
	public StreakTypeCondition(String field) {
		this.field = field;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		ESEntity entity = EnchantmentSolution.getESEntity((LivingEntity) damager);
		if (entity != null) {
			Streak s = entity.getCustomValue(Streak.class, field);
			return s != null && s.getType() == damaged.getType();
		}
		return false;
	}

}
