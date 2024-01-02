package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.ConfigPotionEffect;

public class JoggersEquipEvent extends PotionEffectArmorEquipEvent {

	public JoggersEquipEvent(Player who, int level, ConfigPotionEffect potionEffect) {
		super(who, new EnchantmentLevel(CERegister.JOGGERS, level), potionEffect);
	}

}
