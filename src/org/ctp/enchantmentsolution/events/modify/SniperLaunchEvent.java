package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;

public class SniperLaunchEvent extends ModifySpeedEvent {

	public SniperLaunchEvent(Player who, double speed) {
		super(who, speed);
	}

}
