package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.ESDamageEntityEvent;

public class IronDefenseEvent extends ESDamageEntityEvent {

	private int shieldDamage;
	private ItemStack shield;

	public IronDefenseEvent(LivingEntity damaged, double damage, double newDamage, ItemStack shield, int shieldDamage) {
		super(damaged, damage, newDamage);
		setShield(shield);
		setShieldDamage(shieldDamage);
	}

	public void setNewDamage(double newDamage) {
		if (newDamage > getDamage()) {
			super.setNewDamage(getDamage());
		} else {
			super.setNewDamage(newDamage);
		}
	}

	public int getShieldDamage() {
		return shieldDamage;
	}

	public void setShieldDamage(int shieldDamage) {
		this.shieldDamage = shieldDamage;
	}

	public ItemStack getShield() {
		return shield;
	}

	public void setShield(ItemStack shield) {
		this.shield = shield;
	}

}
