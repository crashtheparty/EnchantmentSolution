package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class PotionEffectArmorRemoveEvent extends PotionEffectEvent {

	public PotionEffectArmorRemoveEvent(Player who, EnchantmentLevel enchantment, PotionEffectType type) {
		super(who, enchantment, type);
	}

}
