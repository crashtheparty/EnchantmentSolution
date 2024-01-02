package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class WaterBreathingUnequipEvent extends PotionEffectArmorUnequipEvent {

	public WaterBreathingUnequipEvent(Player who) {
		super(who, new EnchantmentLevel(CERegister.WATER_BREATHING, 1), PotionEffectType.WATER_BREATHING);
	}

}
