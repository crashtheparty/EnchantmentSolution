package org.ctp.enchantmentsolution.interfaces.conditions.block;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public class BlockIsTypeCondition implements BlockCondition {

	private final boolean opposite;
	private MatData[] materials;

	public BlockIsTypeCondition(boolean opposite, MatData... materials) {
		this.opposite = opposite;
		this.setMaterials(materials);
	}

	@Override
	public boolean metCondition(Player player, BlockData brokenData, BlockEvent event) {
		for (MatData m : materials)
			if (m.hasMaterial() && brokenData.getMaterial() == m.getMaterial()) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

	public MatData[] getMaterials() {
		return materials;
	}

	public void setMaterials(MatData[] materials) {
		this.materials = materials;
	}
}
