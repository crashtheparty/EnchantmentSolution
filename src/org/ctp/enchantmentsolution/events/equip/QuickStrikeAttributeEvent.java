package org.ctp.enchantmentsolution.events.equip;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class QuickStrikeAttributeEvent extends AttributeEvent {

	public QuickStrikeAttributeEvent(Entity who, int level, boolean equip, AttributeModifier modifier) {
		super(who, new EnchantmentLevel(CERegister.QUICK_STRIKE, level), equip, modifier);
	}
}