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
import org.ctp.crashapi.nms.DamageNMS;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.equip.LifeAttributeEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.equip.HealthChangeEffect;

public class Life extends HealthChangeEffect {

	public Life() {
		super(RegisterEnchantments.LIFE, EnchantmentMultipleType.STACK, EnchantmentItemLocation.WEARING, EventPriority.HIGHEST, 0, 1, "5 * %level%", "1", false, false, true, false, UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), "life_health", Operation.ADD_NUMBER, new EquipCondition[0]);
	}

	@Override
	public HealthChangeResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		HumanEntity human = (HumanEntity) entity;
		HealthChangeResult result = super.run(entity, items, oldLevel, event);
		AttributeInstance instance = human.getAttribute(getAttr());
		int level = result.getLevel();

		if (oldLevel > level && level > 0 || event.getMethod() == EquipMethod.JOIN && level > 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			LifeAttributeEvent life = new LifeAttributeEvent(human, oldLevel, false, modifier);
			Bukkit.getPluginManager().callEvent(life);
			if (!life.isCancelled()) {
				try {
					addModifier(human, modifier, new String[0]);
					if (hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier, "helmet_life", "chestplate_life", "leggings_life", "boots_life")) instance.removeModifier(modifier);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				modifier = result.getModifier();
				life = new LifeAttributeEvent(human, level, true, result.getModifier());
				Bukkit.getPluginManager().callEvent(life);
				if (!life.isCancelled()) {
					modifier = life.getModifier();
					addModifier(human, modifier, new String[0]);
					DamageNMS.updateHealth(human);
					return new HealthChangeResult(level, modifier);
				}
			}
		} else if (level > oldLevel) {
			AttributeModifier modifier = result.getModifier();
			LifeAttributeEvent life = new LifeAttributeEvent(human, level, true, result.getModifier());
			Bukkit.getPluginManager().callEvent(life);
			if (!life.isCancelled()) {
				modifier = life.getModifier();
				addModifier(human, modifier, new String[0]);
				DamageNMS.updateHealth(human);
				return new HealthChangeResult(level, modifier);
			}
		} else if (oldLevel > 0 && level == 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			LifeAttributeEvent life = new LifeAttributeEvent(human, oldLevel, false, modifier);
			Bukkit.getPluginManager().callEvent(life);
			if (!life.isCancelled()) {
				try {
					addModifier(human, modifier, new String[0]);
					if (hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier, "helmet_life", "chestplate_life", "leggings_life", "boots_life")) instance.removeModifier(modifier);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				DamageNMS.updateHealth(human);
				return null;
			}
		}

		return null;
	}

}
