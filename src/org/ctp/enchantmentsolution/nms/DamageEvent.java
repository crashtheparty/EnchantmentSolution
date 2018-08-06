package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.nms.damage.DamageEventv1_13_R1;

public class DamageEvent {
	
	public static void damageEntity(LivingEntity entity, String cause, float damage) {
		switch(Version.VERSION_NUMBER) {
		case 1:
			DamageEventv1_13_R1.damageEntity(entity, cause, damage);
			break;
		}
	}
	
}
