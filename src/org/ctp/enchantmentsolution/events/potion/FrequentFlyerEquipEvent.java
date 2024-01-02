package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.ConfigCustomPotionEffect;

public class FrequentFlyerEquipEvent extends CustomPotionEffectArmorEquipEvent {

	public FrequentFlyerEquipEvent(Player who, int level, ConfigCustomPotionEffect potionEffect) {
		super(who, new EnchantmentLevel(CERegister.FREQUENT_FLYER, level), potionEffect);
	}

}
