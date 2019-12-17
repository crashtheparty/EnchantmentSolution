package org.ctp.enchantmentsolution.nms.damage;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.DamageSource;
import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.EntityArrow;
import net.minecraft.server.v1_15_R1.EntityLiving;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MathHelper;

public class DamageEvent_v1_15_R1 {

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
		EntityPlayer player = (EntityPlayer) ((CraftPlayer) p).getHandle();
		switch (cause) {
			case "arrow":
				source = DamageSource.playerAttack(player);
		}
		
		entity.damageEntity(source, damage);
	}

	public static double getArrowDamage(LivingEntity le, Arrow a) {
		EntityArrow arrow = ((CraftArrow) a).getHandle();
		EntityLiving entity = ((CraftLivingEntity) le).getHandle();
        float f = (float) arrow.getMot().f();
        int i = MathHelper.f(Math.max((double) f * arrow.getDamage(), 0.0D));
		arrow.a(entity, i);
		return (int) arrow.getDamage() / 2;
	}

}
