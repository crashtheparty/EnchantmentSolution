package org.ctp.enchantmentsolution.interfaces.conditions.block;

import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public class BlockIsAgeableCondition implements BlockCondition {

	private final boolean opposite;

	public BlockIsAgeableCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Player player, BlockData brokenData, BlockEvent event) {
		if (brokenData instanceof Ageable) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

}
