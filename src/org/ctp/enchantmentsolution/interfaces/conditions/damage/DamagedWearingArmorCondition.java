package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class DamagedWearingArmorCondition implements DamageCondition {

	private final boolean opposite;

	public DamagedWearingArmorCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		if (damaged instanceof HumanEntity) {
			HumanEntity human = (HumanEntity) damaged;
			for(ItemStack i: human.getInventory().getArmorContents())
				if (i != null) return !opposite;
		}
		return opposite;
	}

}
