package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class SplatterFestEvent extends ESPlayerEvent {

	private boolean takeEgg, hasEgg;

	public SplatterFestEvent(Player who, boolean takeEgg, boolean hasEgg) {
		super(who);
		setTakeEgg(takeEgg);
		setHasEgg(hasEgg);
	}

	public boolean takeEgg() {
		return takeEgg;
	}

	public void setTakeEgg(boolean takeEgg) {
		this.takeEgg = takeEgg;
	}

	public boolean hasEgg() {
		return hasEgg;
	}

	public void setHasEgg(boolean hasEgg) {
		this.hasEgg = hasEgg;
	}

}
