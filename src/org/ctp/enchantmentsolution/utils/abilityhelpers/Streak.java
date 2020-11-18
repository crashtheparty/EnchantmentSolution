package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class Streak {

	private EntityType type;
	private int streak;

	public int addToStreak(LivingEntity entity) {
		if (entity.getType() == type) streak++;
		else {
			type = entity.getType();
			streak = 0;
		}
		return streak;
	}

	public int getStreak() {
		return streak;
	}

	public EntityType getType() {
		return type;
	}

	public void setStreak(EntityType type, int streak) {
		this.type = type;
		this.streak = streak;
	}

}
