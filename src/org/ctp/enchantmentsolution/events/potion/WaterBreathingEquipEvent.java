package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.ConfigPotionEffect;

public class WaterBreathingEquipEvent extends PotionEffectArmorEquipEvent {

	public WaterBreathingEquipEvent(Player who, ConfigPotionEffect potionEffect) {
		super(who, new EnchantmentLevel(CERegister.WATER_BREATHING, 1), potionEffect);
	}

}
