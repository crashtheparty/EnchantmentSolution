package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class UnrestUnequipEvent extends PotionEffectArmorUnequipEvent {

	public UnrestUnequipEvent(Player who) {
		super(who, new EnchantmentLevel(CERegister.UNREST, 1), PotionEffectType.WATER_BREATHING);
	}

}
