package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.nms.damage.DamageEvent_v1_13_R2;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.damage.DamageEvent_v1_13_R1;

public class DamageEvent {
	
	public static void damageEntity(LivingEntity entity, String cause, float damage) {
		switch(EnchantmentSolution.getBukkitVersion().getVersionNumber()) {
		case 1:
			DamageEvent_v1_13_R1.damageEntity(entity, cause, damage);
			break;
		case 2:
		case 3:
			DamageEvent_v1_13_R2.damageEntity(entity, cause, damage);
			break;
		}
	}
	
}
