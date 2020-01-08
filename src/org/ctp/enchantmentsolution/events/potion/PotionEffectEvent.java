package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class PotionEffectEvent extends ESEntityEvent {

	private PotionEffectType type;

	public PotionEffectEvent(LivingEntity who, EnchantmentLevel enchantment, PotionEffectType type) {
		super(who, enchantment);
		setType(type);
	}

	public PotionEffectType getType() {
		return type;
	}

	public void setType(PotionEffectType type) {
		this.type = type;
	}

}
