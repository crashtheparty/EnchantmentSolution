package org.ctp.enchantmentsolution.nms.damage;

import org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

import net.minecraft.server.v1_13_R1.DamageSource;

public class DamageEventv1_13_R1 {
	
	public static void damageEntity(LivingEntity e, String cause, float damage) {
		DamageSource source = DamageSource.GENERIC;
		switch(cause) {
		case "drown":
			source = DamageSource.DROWN;
		}
		((CraftEntity)e).getHandle().damageEntity(source, damage);
	}

}
