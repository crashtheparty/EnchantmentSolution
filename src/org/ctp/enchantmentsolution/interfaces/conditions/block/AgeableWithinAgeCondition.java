package org.ctp.enchantmentsolution.interfaces.conditions.block;

import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public class AgeableWithinAgeCondition implements BlockCondition {

	private final boolean opposite;
	private final int minAge, maxAge;

	public AgeableWithinAgeCondition(boolean opposite, int minAge, int maxAge) {
		this.opposite = opposite;
		this.minAge = minAge;
		this.maxAge = maxAge;
	}

	@Override
	public boolean metCondition(Player player, BlockData brokenData, BlockEvent event) {
		if (brokenData instanceof Ageable) {
			Ageable ageable = (Ageable) brokenData;
			if (ageable.getAge() >= minAge && ageable.getAge() <= maxAge) return !opposite;
		}
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

	public int getMinAge() {
		return minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

}
