package org.ctp.enchantmentsolution.events.equip;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class GungHoAttributeEvent extends AttributeEvent {

	public GungHoAttributeEvent(Entity who, boolean equip, AttributeModifier modifier) {
		super(who, new EnchantmentLevel(CERegister.GUNG_HO, 1), equip, modifier);
	}
}