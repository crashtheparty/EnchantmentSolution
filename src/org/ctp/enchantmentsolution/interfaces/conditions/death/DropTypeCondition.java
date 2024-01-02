package org.ctp.enchantmentsolution.interfaces.conditions.death;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;

public class DropTypeCondition implements DeathCondition {

	private final boolean opposite;
	private final MatData[] types;

	public DropTypeCondition(boolean opposite, MatData[] types) {
		this.opposite = opposite;
		this.types = types;
	}

	@Override
	public boolean metCondition(Entity killer, Entity killed, Collection<ItemStack> drops) {
		for(ItemStack drop: drops)
			for(MatData data: types)
				if (drop.getType() == data.getMaterial()) return !opposite;

		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

	public MatData[] getTypes() {
		return types;
	}

}
