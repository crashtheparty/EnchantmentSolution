package org.ctp.enchantmentsolution.events.fishing;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.FishingEvent;

public class FriedEvent extends FishingEvent {

	public FriedEvent(Player who, Material fish) {
		super(who, fish);
	}

}
