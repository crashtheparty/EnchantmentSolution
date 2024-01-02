package org.ctp.enchantmentsolution.events.modify.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ModifyPlayerSpeedEvent extends ModifyPlayerActionEvent {

	private double speed;

	public ModifyPlayerSpeedEvent(Player who, EnchantmentLevel enchantment, double speed) {
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
