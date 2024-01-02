package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class PotionEffectArmorUnequipEvent extends PotionEffectEvent {

	public PotionEffectArmorUnequipEvent(Player who, EnchantmentLevel enchantment, PotionEffectType type) {
		super(who, enchantment, type);
	}

}
