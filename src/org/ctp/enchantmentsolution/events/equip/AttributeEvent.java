package org.ctp.enchantmentsolution.events.equip;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class AttributeEvent extends ESEntityEvent {

	private boolean equip;
	private AttributeModifier modifier;

	public AttributeEvent(Entity who, EnchantmentLevel enchantment, boolean equip, AttributeModifier modifier) {
		super(who, enchantment);
		this.equip = equip;
		this.modifier = modifier;
	}

	public boolean isEquip() {
		return equip;
	}

	public void setEquip(boolean equip) {
		this.equip = equip;
	}

	public AttributeModifier getModifier() {
		return modifier;
	}

	public void setModifier(AttributeModifier modifier) {
		this.modifier = modifier;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

}
