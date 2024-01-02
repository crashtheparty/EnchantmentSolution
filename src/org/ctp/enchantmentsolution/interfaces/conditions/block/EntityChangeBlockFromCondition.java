package org.ctp.enchantmentsolution.interfaces.conditions.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.interfaces.conditions.EntityBlockCondition;

public class EntityChangeBlockFromCondition implements EntityBlockCondition {
	private final boolean opposite;
	private final MatData[] data;

	public EntityChangeBlockFromCondition(boolean opposite, MatData... data) {
		this.opposite = opposite;
		this.data = data;
	}

	@Override
	public boolean metCondition(Entity entity, Block block, EntityChangeBlockEvent event) {
		for (MatData mat : data)
			if (mat.hasMaterial() && mat.getMaterial() == event.getBlock().getType()) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

	public MatData[] getData() {
		return data;
	}
}