package org.ctp.enchantmentsolution.interfaces.conditions.projectile;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;

public class IsBlockingCondition implements ProjectileHitCondition {

	private final boolean opposite;

	public IsBlockingCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(LivingEntity damaged, LivingEntity shooter, ProjectileHitEvent event) {
		if (damaged instanceof Player && ((Player) damaged).isBlocking()) return !opposite;
		return opposite;
	}

	public boolean getOpposite() {
		return opposite;
	}

}
