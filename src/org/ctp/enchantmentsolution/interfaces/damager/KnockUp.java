package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.entity.KnockUpEntityEvent;
import org.ctp.enchantmentsolution.events.modify.player.KnockUpPlayerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamageCauseCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.VelocityEffect;

public class KnockUp extends VelocityEffect {

	public KnockUp() {
		super(RegisterEnchantments.KNOCKUP, EnchantmentMultipleType.ALL, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, "0", "0.275184010449", "0", "0", "0.18 * %level%", "0", false, true, false, false, true, false, true, 1.5, new DamageCondition[] { new DamageCauseCondition(true, DamageCause.ENTITY_SWEEP_ATTACK) });
	}

	@Override
	public VelocityResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		setEntity(damaged);
		VelocityResult result = super.run(damager, damaged, items, event);
		Entity entity = result.getEntity();

		int level = result.getLevel();
		if (level == 0) return null;

		double x = result.getX();
		double y = result.getY();
		double z = result.getZ();

		if (entity instanceof Player) {
			KnockUpPlayerEvent knockUp = new KnockUpPlayerEvent((Player) entity, level, (LivingEntity) damager, x, y, z);
			Bukkit.getPluginManager().callEvent(knockUp);
			if (!knockUp.isCancelled()) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> {
				double knockupX = knockUp.getX();
				double knockupY = knockUp.getY();
				double knockupZ = knockUp.getZ();
				if (entity.isDead() && useVelocityDeathChange()) {
					knockupX /= getVelocityDeathChange();
					knockupY /= getVelocityDeathChange();
					knockupZ /= getVelocityDeathChange();
				}
				entity.setVelocity(new Vector(knockupX, knockupY, knockupZ));
			}, 0l);
		} else {
			KnockUpEntityEvent knockUp = new KnockUpEntityEvent((LivingEntity) entity, level, (LivingEntity) damager, x, y, z);
			Bukkit.getPluginManager().callEvent(knockUp);
			if (!knockUp.isCancelled()) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> {
				double knockupX = knockUp.getX();
				double knockupY = knockUp.getY();
				double knockupZ = knockUp.getZ();
				if (entity.isDead() && useVelocityDeathChange()) {
					knockupX /= getVelocityDeathChange();
					knockupY /= getVelocityDeathChange();
					knockupZ /= getVelocityDeathChange();
				}
				entity.setVelocity(new Vector(knockupX, knockupY, knockupZ));
			}, 0l);
		}
		return null;
	}
}
