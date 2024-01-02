package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class PlyometricsUnequipEvent extends PotionEffectArmorUnequipEvent {

	public PlyometricsUnequipEvent(Player who, int level) {
		super(who, new EnchantmentLevel(CERegister.PLYOMETRICS, level), PotionEffectType.JUMP);
	}

}
