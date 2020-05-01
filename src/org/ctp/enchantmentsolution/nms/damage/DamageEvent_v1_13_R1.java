package org.ctp.enchantmentsolution.nms.damage;

import org.bukkit.craftbukkit.v1_13_R1.entity.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_13_R1.*;

public class DamageEvent_v1_13_R1 {

	public static void damageEntity(LivingEntity e, String cause, float damage) {
		DamageSource source = DamageSource.GENERIC;
		switch (cause) {
			case "drown":
				source = DamageSource.DROWN;
		}
		((CraftEntity) e).getHandle().damageEntity(source, damage);
	}

	public static void damageEntity(LivingEntity e, Player p, String cause, float damage) {
		DamageSource source = DamageSource.GENERIC;
		Entity entity = ((CraftEntity) e).getHandle();
		EntityPlayer player = ((CraftPlayer) p).getHandle();
		switch (cause) {
			case "arrow":
				source = DamageSource.playerAttack(player);
		}

		entity.damageEntity(source, damage);
	}

	public static double getArrowDamage(LivingEntity le, Arrow a) {
		EntityArrow arrow = ((CraftArrow) a).getHandle();
		EntityLiving entity = ((CraftLivingEntity) le).getHandle();
		Vec3D d = new Vec3D(arrow.motX, arrow.motY, arrow.motZ);
		float f = (float) d.b();
		int i = MathHelper.f(Math.max(f * arrow.getDamage(), 0.0D));
		arrow.a(entity, i);
		return (int) arrow.getDamage() / 2;
	}

	public static void updateHealth(LivingEntity le) {
		Entity entity = ((CraftEntity) le).getHandle();
		if (entity instanceof EntityLiving) {
			EntityLiving living = (EntityLiving) entity;
			living.setHealth(living.getHealth());
		}
	}

}
