package org.ctp.enchantmentsolution.interfaces.conditions.death;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.Lootable;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;

public class KilledIsLootableCondition implements DeathCondition {

	private boolean opposite;

	public KilledIsLootableCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Entity killer, Entity killed, Collection<ItemStack> drops) {
		if (killed instanceof Lootable) return !opposite;
		return opposite;
	}

}
