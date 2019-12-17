package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class AttributeEvent extends ESPlayerEvent {

	private String removeModifier, addModifier;
	private EnchantmentLevel enchantment;

	public AttributeEvent(Player who, EnchantmentLevel enchantment, String removeModifier, String addModifier) {
		super(who, enchantment);
		setEnchantment(enchantment);
		setRemoveModifier(removeModifier);
		setAddModifier(addModifier);
	}

	public String getRemoveModifier() {
		return removeModifier;
	}

	public void setRemoveModifier(String removeModifier) {
		this.removeModifier = removeModifier;
	}

	public String getAddModifier() {
		return addModifier;
	}

	public void setAddModifier(String addModifier) {
		this.addModifier = addModifier;
	}

	public EnchantmentLevel getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(EnchantmentLevel enchantment) {
		this.enchantment = enchantment;
	}
}
