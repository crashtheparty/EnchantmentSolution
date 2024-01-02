package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public class CustomPotionEffectArmorUnequipEvent extends CustomPotionEffectEvent {

	public CustomPotionEffectArmorUnequipEvent(Player who, EnchantmentLevel enchantment, CustomPotionEffectType type) {
		super(who, enchantment, type);
	}

}
