package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class JoggersUnequipEvent extends PotionEffectArmorUnequipEvent {

	public JoggersUnequipEvent(Player who, int level) {
		super(who, new EnchantmentLevel(CERegister.JOGGERS, level), PotionEffectType.SPEED);
	}

}
