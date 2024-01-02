package org.ctp.enchantmentsolution.interfaces.conditions.projectile;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;

public class ProjectileHasMetadataCondition implements ProjectileHitCondition {

	private final boolean opposite;
	private final String[] data;

	public ProjectileHasMetadataCondition(boolean opposite, String... data) {
		this.opposite = opposite;
		this.data = data;
	}

	@Override
	public boolean metCondition(LivingEntity damaged, LivingEntity shooter, ProjectileHitEvent event) {
		Projectile p = event.getEntity();
		for(String s: data)
			if (p.hasMetadata(s)) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

	public String[] getData() {
		return data;
	}

}
