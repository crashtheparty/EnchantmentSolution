package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class SplatterFestEvent extends ESPlayerEvent {

	private boolean takeEgg;
	private final boolean hasEgg;

	public SplatterFestEvent(Player who, boolean takeEgg, boolean hasEgg) {
		super(who, new EnchantmentLevel(CERegister.SPLATTER_FEST, 1));
		setTakeEgg(takeEgg);
		this.hasEgg = hasEgg;
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

}
