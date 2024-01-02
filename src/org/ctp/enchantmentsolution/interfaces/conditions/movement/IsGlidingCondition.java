package org.ctp.enchantmentsolution.interfaces.conditions.movement;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.MovementCondition;

public class IsGlidingCondition implements MovementCondition {

	private boolean opposite;
	
	public IsGlidingCondition(boolean opposite) {
		this.setOpposite(opposite);
	}
	
	@Override
	public boolean metCondition(Player player, PlayerChangeCoordsEvent event) {
		if (player.isGliding()) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

	public void setOpposite(boolean opposite) {
		this.opposite = opposite;
	}

}
