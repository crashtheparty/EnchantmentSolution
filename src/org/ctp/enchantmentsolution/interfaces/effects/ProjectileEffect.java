package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.event.EventPriority;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileCondition;

public abstract class ProjectileEffect extends EnchantmentEffect {

	private final ProjectileCondition[] conditions;

	public ProjectileEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	ProjectileCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	protected ProjectileCondition[] getConditions() {
		return conditions;
	}

}
