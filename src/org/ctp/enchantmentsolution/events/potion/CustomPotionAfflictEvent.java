package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public class CustomPotionAfflictEvent extends CustomPotionEffectEvent {
	private int duration, level;
	private boolean override;
	private final LivingEntity afflicter;

	public CustomPotionAfflictEvent(LivingEntity who, LivingEntity afflicter, EnchantmentLevel enchantment, CustomPotionEffectType type, int duration,
	int level,
	boolean override) {
		super(who, enchantment, type);
		this.afflicter = afflicter;
		setDuration(duration);
		setLevel(level);
		setOverride(override);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public LivingEntity getAfflicter() {
		return afflicter;
	}

}
