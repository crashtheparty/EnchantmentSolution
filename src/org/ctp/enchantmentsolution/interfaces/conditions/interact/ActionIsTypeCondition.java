package org.ctp.enchantmentsolution.interfaces.conditions.interact;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;

public class ActionIsTypeCondition implements InteractCondition {

	private final boolean opposite;
	private final Action[] actions;

	public ActionIsTypeCondition(boolean opposite, Action... actions) {
		this.opposite = opposite;
		this.actions = actions;
	}

	@Override
	public boolean metCondition(Player player, PlayerInteractEvent event) {
		for(Action a: actions)
			if (event.getAction() == a) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

	public Action[] getActions() {
		return actions;
	}

}
