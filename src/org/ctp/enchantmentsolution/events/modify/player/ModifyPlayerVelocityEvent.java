package org.ctp.enchantmentsolution.events.modify.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ModifyPlayerVelocityEvent extends ModifyPlayerActionEvent {

	private double x, y, z;

	public ModifyPlayerVelocityEvent(Player who, EnchantmentLevel enchantment, double x, double y, double z) {
		super(who, enchantment);
		setX(x);
		setY(y);
		setZ(z);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
