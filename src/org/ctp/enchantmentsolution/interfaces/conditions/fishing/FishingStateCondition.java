package org.ctp.enchantmentsolution.interfaces.conditions.fishing;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.FishingCondition;

public class FishingStateCondition implements FishingCondition {

	private final boolean opposite;
	private final PlayerFishEvent.State[] states;

	public FishingStateCondition(boolean opposite, PlayerFishEvent.State... states) {
		this.opposite = opposite;
		this.states = states;
	}

	@Override
	public boolean metCondition(Player player, PlayerFishEvent event) {
		for(PlayerFishEvent.State state: states)
			if (state == event.getState()) return !opposite;
		return opposite;
	}

	public boolean getOpposite() {
		return opposite;
	}

	public PlayerFishEvent.State[] getStates() {
		return states;
	}

}
