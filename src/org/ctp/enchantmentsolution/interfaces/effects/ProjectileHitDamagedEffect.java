package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;

public abstract class ProjectileHitDamagedEffect extends ProjectileEffect {

	public ProjectileHitDamagedEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	ProjectileHitCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

	public boolean willRun(LivingEntity shooter, LivingEntity damaged, ProjectileHitEvent event) {
		for(ProjectileCondition condition: getConditions())
			if (!((ProjectileHitCondition) condition).metCondition(damaged, shooter, event)) return false;
		return true;
	}

	public abstract EffectResult run(LivingEntity shooter, LivingEntity damaged, Projectile projectile, ItemStack[] items, ProjectileHitEvent event);

}