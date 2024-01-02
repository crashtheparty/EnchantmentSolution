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
import org.ctp.enchantmentsolution.events.equip.QuickStrikeAttributeEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.equip.AttackSpeedEffect;

public class QuickStrike extends AttackSpeedEffect {

	public QuickStrike() {
		super(RegisterEnchantments.QUICK_STRIKE, EnchantmentMultipleType.STACK, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, 1, "0.5 * %level%", "1", false, false, true, false, UUID.fromString("dddddddd-fefe-fefe-fefe-000000000000"), "quick_strike_speed", Operation.ADD_NUMBER, new EquipCondition[0]);
	}

	@Override
	public SpeedResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		HumanEntity human = (HumanEntity) entity;
		SpeedResult result = super.run(entity, items, oldLevel, event);
		AttributeInstance instance = human.getAttribute(getAttr());
		int level = result.getLevel();
		
		if (oldLevel > level && level > 0 || event.getMethod() == EquipMethod.JOIN && level > 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			QuickStrikeAttributeEvent quickStrike = new QuickStrikeAttributeEvent(human, oldLevel, false, modifier);
			Bukkit.getPluginManager().callEvent(quickStrike);
			if (!quickStrike.isCancelled()) {
				try {
					addModifier(human, modifier, new String[0]);
					if (hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier)) instance.removeModifier(modifier);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				modifier = result.getModifier();
				quickStrike = new QuickStrikeAttributeEvent(human, level, true, result.getModifier());
				Bukkit.getPluginManager().callEvent(quickStrike);
				if (!quickStrike.isCancelled()) {
					modifier = quickStrike.getModifier();
					addModifier(human, modifier, new String[0]);
					return new SpeedResult(level, modifier);
				}
			}
		} else if (level > oldLevel) {
			AttributeModifier modifier = result.getModifier();
			QuickStrikeAttributeEvent quickStrike = new QuickStrikeAttributeEvent(human, level, true, result.getModifier());
			Bukkit.getPluginManager().callEvent(quickStrike);
			if (!quickStrike.isCancelled()) {
				modifier = quickStrike.getModifier();
				addModifier(human, modifier, new String[0]);
				return new SpeedResult(level, modifier);
			}
		} else if (oldLevel > 0 && level == 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			QuickStrikeAttributeEvent quickStrike = new QuickStrikeAttributeEvent(human, oldLevel, false, modifier);
			Bukkit.getPluginManager().callEvent(quickStrike);
			if (!quickStrike.isCancelled()) {
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
