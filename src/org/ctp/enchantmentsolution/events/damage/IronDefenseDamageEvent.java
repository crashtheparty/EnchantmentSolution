package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.events.ESDamageEntityEvent;

public class IronDefenseDamageEvent extends ESDamageEntityEvent {

	private int shieldDamage;
	
	public IronDefenseDamageEvent(LivingEntity damaged, double damage, double newDamage, int shieldDamage) {
		super(damaged, damage, newDamage);
		this.setShieldDamage(shieldDamage);
	}
	
	public void setNewDamage(double newDamage) {
		if(newDamage > getDamage()) {
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

}
