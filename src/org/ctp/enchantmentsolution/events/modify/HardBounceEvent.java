package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;

public class HardBounceEvent extends ModifySpeedEvent {

	public HardBounceEvent(Player who, double speed) {
		super(who, speed);
	}

}
