package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.ESPotionEffectEvent;

public class PotionEffectArmorRemoveEvent extends ESPotionEffectEvent {

	public PotionEffectArmorRemoveEvent(Player who, EnchantmentLevel enchantment, PotionEffectType type) {
		super(who, enchantment, type);
	}

}
