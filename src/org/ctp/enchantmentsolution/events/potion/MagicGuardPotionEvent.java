package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.PotionEffectEvent;

public class MagicGuardPotionEvent extends PotionEffectEvent {

	public MagicGuardPotionEvent(Player who, PotionEffectType type) {
		super(who, new EnchantmentLevel(CERegister.MAGIC_GUARD, 1), type);
	}

}
