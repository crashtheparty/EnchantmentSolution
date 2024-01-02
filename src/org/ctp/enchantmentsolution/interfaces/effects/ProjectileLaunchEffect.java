package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileLaunchCondition;

public abstract class ProjectileLaunchEffect extends ProjectileEffect {

	public ProjectileLaunchEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	ProjectileLaunchCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

	public boolean willRun(LivingEntity entity, ProjectileLaunchEvent event) {
		for(ProjectileCondition condition: getConditions())
			if (!((ProjectileLaunchCondition) condition).metCondition(entity, event)) return false;
		return true;
	}

	public abstract EffectResult run(LivingEntity entity, Projectile projectile, ItemStack[] items, ProjectileLaunchEvent event);

}
