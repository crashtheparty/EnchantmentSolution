package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.player.ESEntity;

public class Streak {

	private EntityType type;
	private int streak;
	private final ESEntity esEntity;
	
	public Streak(ESEntity esEntity) {
		this.esEntity = esEntity;
	}

	public int addToStreak(LivingEntity entity) {
		if (entity.getType() == type) streak++;
		else {
			type = entity.getType();
			streak = 0;
		}
		if (streak == 100 && esEntity.getPlayer() != null) AdvancementUtils.awardCriteria(esEntity.getPlayer().getOnlinePlayer(), ESAdvancement.DOUBLE_DAMAGE, "kills");
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
