package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class OverkillEvent extends ProjectileSpawnEvent {

	private boolean takeArrow;
	private final boolean hasArrow;
	private double speed;

	public OverkillEvent(Player who, ItemStack item, boolean takeArrow, boolean hasArrow, double speed) {
		super(who, new EnchantmentLevel(CERegister.OVERKILL, 1), item, 4);
		setTakeArrow(takeArrow);
		this.hasArrow = hasArrow;
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

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public boolean willCancel() {
		return !hasArrow && takeArrow || super.willCancel();
	}

}
