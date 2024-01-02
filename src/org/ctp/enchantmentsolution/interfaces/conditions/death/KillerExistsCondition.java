package org.ctp.enchantmentsolution.interfaces.conditions.death;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;

public class KillerExistsCondition implements DeathCondition {

	private final boolean opposite;

	public KillerExistsCondition(boolean opposite, MobData... entities) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Entity killer, Entity killed, Collection<ItemStack> drops) {
		if (killer != null) return !opposite;
		return opposite;
	}

	public boolean getOpposite() {
		return opposite;
	}
}
