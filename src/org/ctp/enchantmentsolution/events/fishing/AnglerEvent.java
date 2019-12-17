package org.ctp.enchantmentsolution.events.fishing;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.FishingEvent;

public class AnglerEvent extends FishingEvent {

	private int fishAmount;

	public AnglerEvent(Player who, Material fish, int level) {
		super(who, new EnchantmentLevel(CERegister.ANGLER, level), fish);
		fishAmount = level + 1;
	}

	public int getFishAmount() {
		return fishAmount;
	}

	public void setFishAmount(int fishAmount) {
		this.fishAmount = fishAmount;
	}

}
