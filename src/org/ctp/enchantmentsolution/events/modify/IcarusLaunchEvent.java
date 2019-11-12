package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;

public class IcarusLaunchEvent extends ModifySpeedEvent {

	public IcarusLaunchEvent(Player who, double speed) {
		super(who, speed);
	}

}
