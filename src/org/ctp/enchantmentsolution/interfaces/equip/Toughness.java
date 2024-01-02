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
import org.ctp.enchantmentsolution.events.equip.ToughnessAttributeEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.equip.ToughnessEffect;

public class Toughness extends ToughnessEffect {

	public Toughness() {
		super(RegisterEnchantments.TOUGHNESS, EnchantmentMultipleType.ALL, EnchantmentItemLocation.WEARING, EventPriority.NORMAL, 0, 1, "%level%", "1", false, false, true, false, UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000000000"), "toughness", Operation.ADD_NUMBER, new EquipCondition[0]);
	}

	@Override
	public ToughnessResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		HumanEntity human = (HumanEntity) entity;
		ToughnessResult result = super.run(entity, items, oldLevel, event);
		AttributeInstance instance = human.getAttribute(getAttr());
		int level = result.getLevel();
		
		if (oldLevel > level && level > 0 || event.getMethod() == EquipMethod.JOIN && level > 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			ToughnessAttributeEvent toughness = new ToughnessAttributeEvent(human, oldLevel, false, modifier);
			Bukkit.getPluginManager().callEvent(toughness);
			if (!toughness.isCancelled()) {
				try {
					addModifier(human, modifier, new String[0]);
					if (hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier, "helmet_toughness", "chestplate_toughness", "leggings_toughness", "boots_toughness")) instance.removeModifier(modifier);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				modifier = result.getModifier();
				toughness = new ToughnessAttributeEvent(human, level, true, result.getModifier());
				Bukkit.getPluginManager().callEvent(toughness);
				if (!toughness.isCancelled()) {
					modifier = toughness.getModifier();
					addModifier(human, modifier, new String[0]);
					return new ToughnessResult(level, modifier);
				}
			}
		} else if (level > oldLevel) {
			AttributeModifier modifier = result.getModifier();
			ToughnessAttributeEvent toughness = new ToughnessAttributeEvent(human, level, true, result.getModifier());
			Bukkit.getPluginManager().callEvent(toughness);
			if (!toughness.isCancelled()) {
				modifier = toughness.getModifier();
				addModifier(human, modifier, new String[0]);
				return new ToughnessResult(level, modifier);
			}
		} else if (oldLevel > 0 && level == 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			ToughnessAttributeEvent toughness = new ToughnessAttributeEvent(human, oldLevel, false, modifier);
			Bukkit.getPluginManager().callEvent(toughness);
			if (!toughness.isCancelled()) {
				try {
					addModifier(human, modifier, new String[0]);
					if (hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier, "helmet_toughness", "chestplate_toughness", "leggings_toughness", "boots_toughness")) instance.removeModifier(modifier);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return null;
			}
		}

		return null;
	}

}
