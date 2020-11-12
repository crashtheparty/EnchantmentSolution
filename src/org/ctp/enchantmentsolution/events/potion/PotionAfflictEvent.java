package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class PotionAfflictEvent extends PotionEffectEvent {

	private int duration, level;
	private final PotionEffect previousEffect;
	private boolean override;
	private final LivingEntity afflicter;

	public PotionAfflictEvent(LivingEntity who, LivingEntity afflicter, EnchantmentLevel enchantment, PotionEffectType type, int duration, int level,
	PotionEffect previousEffect, boolean override) {
		super(who, enchantment, type);
		this.afflicter = afflicter;
		setDuration(duration);
		setLevel(level);
		this.previousEffect = previousEffect;
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

	public PotionEffect getPreviousEffect() {
		return previousEffect;
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
