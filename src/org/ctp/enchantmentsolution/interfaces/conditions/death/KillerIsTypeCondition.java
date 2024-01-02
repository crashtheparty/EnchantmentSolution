package org.ctp.enchantmentsolution.interfaces.conditions.death;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;

public class KillerIsTypeCondition implements DeathCondition {

	private final boolean opposite;
	private final MobData[] entities;

	public KillerIsTypeCondition(boolean opposite, MobData... entities) {
		this.opposite = opposite;
		this.entities = entities;
	}

	@Override
	public boolean metCondition(Entity killer, Entity killed, Collection<ItemStack> drops) {
		for(MobData entity: entities)
			if (entity.getEntity() == killer.getType()) return !opposite;
		return opposite;
	}

	public boolean getOpposite() {
		return opposite;
	}

	public MobData[] getEntities() {
		return entities;
	}

}
