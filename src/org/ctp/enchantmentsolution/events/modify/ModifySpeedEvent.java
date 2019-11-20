package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.ModifyActionEvent;

public abstract class ModifySpeedEvent extends ModifyActionEvent {

	private double speed;

	public ModifySpeedEvent(Player who, double speed) {
		super(who);
		setSpeed(speed);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
