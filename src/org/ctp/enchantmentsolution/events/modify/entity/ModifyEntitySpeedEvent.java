package org.ctp.enchantmentsolution.events.modify.entity;

import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ModifyEntitySpeedEvent extends ModifyEntityActionEvent {

	private double speedX, speedY, speedZ;

	public ModifyEntitySpeedEvent(Entity who, EnchantmentLevel enchantment, double speedX, double speedY, double speedZ) {
		super(who, enchantment);
		this.speedX = speedX;
		this.speedY = speedY;
		this.speedZ = speedZ;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public double getSpeedZ() {
		return speedZ;
	}

	public void setSpeedZ(double speedZ) {
		this.speedZ = speedZ;
	}

}
