package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.ConfigPotionEffect;

public class PlyometricsEquipEvent extends PotionEffectArmorEquipEvent {

	public PlyometricsEquipEvent(Player who, int level, ConfigPotionEffect potionEffect) {
		super(who, new EnchantmentLevel(CERegister.PLYOMETRICS, level), potionEffect);
	}

}
