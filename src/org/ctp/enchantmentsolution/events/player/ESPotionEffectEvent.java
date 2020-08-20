package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ESPotionEffectEvent extends ESPlayerEvent {

	private final PotionEffectType type;
	
	public ESPotionEffectEvent(Player who, EnchantmentLevel enchantment, PotionEffectType type) {
		super(who, enchantment);
		this.type = type;
	}

	public PotionEffectType getType() {
		return type;
	}

}
