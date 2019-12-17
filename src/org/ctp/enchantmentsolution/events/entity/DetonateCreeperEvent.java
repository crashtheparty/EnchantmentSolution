package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.Creeper;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public class DetonateCreeperEvent extends ESEntityEvent {

	private int detonateTicks;
	
	public DetonateCreeperEvent(Creeper who, int level, int detonateTicks) {
		super(who, new EnchantmentLevel(CERegister.DETONATOR, level));
		this.setDetonateTicks(detonateTicks);
	}

	public int getDetonateTicks() {
		return detonateTicks;
	}

	public void setDetonateTicks(int detonateTicks) {
		this.detonateTicks = detonateTicks;
	}

}
