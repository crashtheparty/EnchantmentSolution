package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public class DetonateCreeperEvent extends ESEntityEvent {

	private int detonateTicks;
	private Entity detonator;

	public DetonateCreeperEvent(Creeper who, int level, int detonateTicks, Entity detonator) {
		super(who, new EnchantmentLevel(CERegister.DETONATOR, level));
		setDetonateTicks(detonateTicks);
		setDetonator(detonator);
	}

	public int getDetonateTicks() {
		return detonateTicks;
	}

	public void setDetonateTicks(int detonateTicks) {
		this.detonateTicks = detonateTicks;
	}

	public Entity getDetonator() {
		return detonator;
	}

	public void setDetonator(Entity detonator) {
		this.detonator = detonator;
	}

}
