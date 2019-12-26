package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class OverkillEvent extends ESPlayerEvent {

	private boolean takeArrow, hasArrow;
	private double speed;

	public OverkillEvent(Player who, boolean takeArrow, boolean hasArrow, double speed) {
		super(who, new EnchantmentLevel(CERegister.OVERKILL, 1));
		setTakeArrow(takeArrow);
		setHasArrow(hasArrow);
		setSpeed(speed);
	}

	public boolean takeArrow() {
		return takeArrow;
	}

	public void setTakeArrow(boolean takeArrow) {
		this.takeArrow = takeArrow;
	}

	public boolean hasArrow() {
		return hasArrow;
	}

	public void setHasArrow(boolean hasArrow) {
		this.hasArrow = hasArrow;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
