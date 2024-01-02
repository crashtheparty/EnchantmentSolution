package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.ConfigPotionEffect;

public class PotionEffectArmorEquipEvent extends PotionEffectEvent {

	private final ConfigPotionEffect potionEffect;
	private ConfigPotionEffect edited;

	public PotionEffectArmorEquipEvent(Player who, EnchantmentLevel enchantment, ConfigPotionEffect potionEffect) {
		super(who, enchantment, potionEffect.getType());
		this.potionEffect = potionEffect;
		edited = potionEffect;
	}

	public int getAmplifier() {
		return edited.getAmplifier(getEnchantment().getLevel());
	}

	public int getDuration() {
		return edited.getDuration(getEnchantment().getLevel());
	}

	public void setPotionEffect(String amplifier, String duration) {
		edited = new ConfigPotionEffect(edited.getType(), amplifier, duration);
	}

	public ConfigPotionEffect getPotionEffect() {
		return potionEffect;
	}

	public ConfigPotionEffect getFinalPotionEffect() {
		return edited;
	}

}
