package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class AttributeEvent extends ESPlayerEvent {

	private final String removeModifier, addModifier;

	public AttributeEvent(Player who, EnchantmentLevel enchantment, String removeModifier, String addModifier) {
		super(who, enchantment);
		this.removeModifier = removeModifier;
		this.addModifier = addModifier;
	}

	public String getRemoveModifier() {
		return removeModifier;
	}

	public String getAddModifier() {
		return addModifier;
	}
}
