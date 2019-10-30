package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.events.ESDamageEntityEvent;

public class IronDefenseListener extends ESDamageEntityEvent {

	public IronDefenseListener(LivingEntity damaged, double damage, double newDamage) {
		super(damaged, damage, newDamage);
	}
	
	public void setNewDamage(double newDamage) {
		if(newDamage > getDamage()) {
			super.setNewDamage(getDamage());
		} else {
			super.setNewDamage(newDamage);
		}
	}

}
