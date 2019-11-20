package org.ctp.enchantmentsolution.events.fishing;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.FishingEvent;

public class AnglerEvent extends FishingEvent {

	private int level, fishAmount;

	public AnglerEvent(Player who, Material fish, int level) {
		super(who, fish);
		this.level = level;
		fishAmount = this.level + 1;
	}

	public int getLevel() {
		return level;
	}

	public int getFishAmount() {
		return fishAmount;
	}

	public void setFishAmount(int fishAmount) {
		this.fishAmount = fishAmount;
	}

}
