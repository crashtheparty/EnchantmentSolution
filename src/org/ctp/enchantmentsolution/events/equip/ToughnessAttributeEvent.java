package org.ctp.enchantmentsolution.events.equip;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class ToughnessAttributeEvent extends AttributeEvent {

	public ToughnessAttributeEvent(Entity who, int level, boolean equip, AttributeModifier modifier) {
		super(who, new EnchantmentLevel(CERegister.TOUGHNESS, level), equip, modifier);
	}
}