package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ModifySpeedEvent extends ModifyActionEvent {

	private double speed;

	public ModifySpeedEvent(Player who, EnchantmentLevel enchantment, double speed) {
		super(who, enchantment);
		setSpeed(speed);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
