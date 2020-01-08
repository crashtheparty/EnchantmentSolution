package org.ctp.enchantmentsolution.events.fishing;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class FishingEvent extends ESPlayerEvent {

	private Material fish;

	public FishingEvent(Player who, EnchantmentLevel enchantment, Material fish) {
		super(who, enchantment);
		this.fish = fish;
	}

	public Material getFish() {
		return fish;
	}

	public void setFish(Material fish) {
		this.fish = fish;
	}

}
