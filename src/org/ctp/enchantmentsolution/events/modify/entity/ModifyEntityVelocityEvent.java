package org.ctp.enchantmentsolution.events.modify.entity;

import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ModifyEntityVelocityEvent extends ModifyEntityActionEvent {

	private double x, y, z;

	public ModifyEntityVelocityEvent(Entity entity, EnchantmentLevel enchantment, double x, double y, double z) {
		super(entity, enchantment);
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
