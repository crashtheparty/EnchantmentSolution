package org.ctp.enchantmentsolution.interfaces.conditions.block;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.ctp.enchantmentsolution.events.blocks.BlockBreakMultiEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public class BlockIsBreakingCondition implements BlockCondition {
	private final boolean opposite;

	public BlockIsBreakingCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Player player, BlockData brokenData, BlockEvent event) {
		if (event instanceof BlockBreakMultiEvent) return opposite;
		return !opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

}
