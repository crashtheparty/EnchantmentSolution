package org.ctp.enchantmentsolution.interfaces.conditions.block;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.mcmmo.McMMOAbility;

public class BlockEntityIgnoredCondition implements BlockCondition {
	private final boolean opposite;

	public BlockEntityIgnoredCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Player player, BlockData brokenData, BlockEvent event) {
		if (!EnchantmentSolution.getPlugin().getMcMMOType().equals("Disabled") && McMMOAbility.getIgnored() != null && McMMOAbility.getIgnored().contains(player)) return opposite;
		return !opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}
}
