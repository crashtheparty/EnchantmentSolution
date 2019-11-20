package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class LootingEvent extends ESPlayerEvent {

	public LootingEvent(Player who) {
		super(who);
	}

}
