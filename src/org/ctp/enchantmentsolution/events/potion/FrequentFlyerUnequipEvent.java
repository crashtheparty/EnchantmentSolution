package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public class FrequentFlyerUnequipEvent extends CustomPotionEffectArmorUnequipEvent {

	public FrequentFlyerUnequipEvent(Player who, int level) {
		super(who, new EnchantmentLevel(CERegister.FREQUENT_FLYER, level), CustomPotionEffectType.FLIGHT);
	}

}
