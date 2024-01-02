package org.ctp.enchantmentsolution.interfaces.conditions.interact;

import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;

public class BlockIsLitCondition implements InteractCondition {

	private final boolean opposite;

	public BlockIsLitCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Player player, PlayerInteractEvent event) {
		if (event.getClickedBlock() == null) return opposite;
		BlockData data = event.getClickedBlock().getBlockData();
		if (data instanceof Lightable && ((Lightable) data).isLit()) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

}
