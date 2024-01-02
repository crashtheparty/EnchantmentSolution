package org.ctp.enchantmentsolution.interfaces.equip;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.crashapi.events.EquipEvent.EquipMethod;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.equip.ArmoredAttributeEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.equip.ArmorEffect;

public class Armored extends ArmorEffect {

	public Armored() {
		super(RegisterEnchantments.ARMORED, EnchantmentMultipleType.ALL, EnchantmentItemLocation.WEARING, EventPriority.NORMAL, 0, 1, "2 * %level%", "1", false, false, true, false, UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001000"), "armored_armor", Operation.ADD_NUMBER, new EquipCondition[] {});
	}

	@Override
	public ArmorResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		HumanEntity human = (HumanEntity) entity;
		ArmorResult result = super.run(entity, items, oldLevel, event);
		int level = result.getLevel();
		AttributeInstance instance = human.getAttribute(getAttr());

		if (oldLevel > level && level > 0 || event.getMethod() == EquipMethod.JOIN && level > 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			ArmoredAttributeEvent armored = new ArmoredAttributeEvent(human, oldLevel, false, modifier);
			Bukkit.getPluginManager().callEvent(armored);
			if (!armored.isCancelled()) {
				try {
					addModifier(human, modifier, new String[0]);
					if (hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier)) instance.removeModifier(modifier);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				modifier = result.getModifier();
				armored = new ArmoredAttributeEvent(human, level, true, result.getModifier());
				Bukkit.getPluginManager().callEvent(armored);
				if (!armored.isCancelled()) {
					modifier = armored.getModifier();
					addModifier(human, modifier, new String[0]);
					return new ArmorResult(level, modifier);
				}
			}
		} else if (level > oldLevel) {
			AttributeModifier modifier = result.getModifier();
			ArmoredAttributeEvent armored = new ArmoredAttributeEvent(human, level, true, result.getModifier());
			Bukkit.getPluginManager().callEvent(armored);
			if (!armored.isCancelled()) {
				modifier = armored.getModifier();
				addModifier(human, modifier, new String[0]);
				return new ArmorResult(level, modifier);
			}
		} else if (oldLevel > 0 && level == 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			ArmoredAttributeEvent armored = new ArmoredAttributeEvent(human, oldLevel, false, modifier);
			Bukkit.getPluginManager().callEvent(armored);
			if (!armored.isCancelled()) {
				try {
					addModifier(human, modifier, new String[0]);
					if (hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier)) instance.removeModifier(modifier);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return null;
			}
		}

		return null;
	}

}
