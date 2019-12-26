package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class PotionEffectEvent extends ESEntityEvent {

	private boolean cancelled;
	private PotionEffectType type;

	public PotionEffectEvent(LivingEntity who, EnchantmentLevel enchantment, PotionEffectType type) {
		super(who, enchantment);
		setType(type);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public PotionEffectType getType() {
		return type;
	}

	public void setType(PotionEffectType type) {
		this.type = type;
	}

}
