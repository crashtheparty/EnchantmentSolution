package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public abstract class CustomPotionEffectEvent extends ESEntityEvent {

	private CustomPotionEffectType type;

	public CustomPotionEffectEvent(Entity who, EnchantmentLevel enchantment, CustomPotionEffectType type) {
		super(who, enchantment);
		setType(type);
	}

	public CustomPotionEffectType getType() {
		return type;
	}

	public void setType(CustomPotionEffectType type) {
		this.type = type;
	}

}
