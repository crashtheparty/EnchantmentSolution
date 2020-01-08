package org.ctp.enchantmentsolution.events.fishing;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class FriedEvent extends FishingEvent {

	public FriedEvent(Player who, Material fish) {
		super(who, new EnchantmentLevel(CERegister.FRIED, 1), fish);
	}

}
