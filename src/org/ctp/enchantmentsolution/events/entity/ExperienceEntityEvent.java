package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class ExperienceEntityEvent extends ESEntityEvent {

	private boolean cancelled = false;
	private final int oldExp;
	private int newExp;

	public ExperienceEntityEvent(LivingEntity who, EnchantmentLevel level, int oldExp, int newExp) {
		super(who, level);
		this.oldExp = oldExp;
		setNewExp(newExp);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public int getOldExp() {
		return oldExp;
	}

	public int getNewExp() {
		return newExp;
	}

	public void setNewExp(int newExp) {
		this.newExp = newExp;
	}
}