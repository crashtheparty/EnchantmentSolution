package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.potion.ConfigCustomPotionEffect;

public class CustomPotionEffectArmorEquipEvent extends CustomPotionEffectEvent {

	private final ConfigCustomPotionEffect potionEffect;
	private ConfigCustomPotionEffect edited;

	public CustomPotionEffectArmorEquipEvent(Player who, EnchantmentLevel enchantment, ConfigCustomPotionEffect potionEffect) {
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
		edited = new ConfigCustomPotionEffect(edited.getType(), amplifier, duration);
	}

	public ConfigCustomPotionEffect getPotionEffect() {
		return potionEffect;
	}

	public ConfigCustomPotionEffect getFinalPotionEffect() {
		return edited;
	}

}
