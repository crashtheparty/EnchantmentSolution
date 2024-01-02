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
import org.ctp.enchantmentsolution.events.equip.GungHoAttributeEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.equip.HealthChangeEffect;

public class GungHoEquip extends HealthChangeEffect {

	public GungHoEquip() {
		super(RegisterEnchantments.GUNG_HO, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.WEARING, EventPriority.HIGH, 0, -0.75, "0", "1", false, true, false, false, UUID.fromString("eeeeeeee-ffff-ffff-ffff-000000000000"), "gung_ho_health", Operation.ADD_SCALAR, new EquipCondition[0]);
	}

	@Override
	public HealthChangeResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		HumanEntity human = (HumanEntity) entity;
		HealthChangeResult result = super.run(entity, items, oldLevel, event);
		AttributeInstance instance = human.getAttribute(getAttr());
		int level = result.getLevel();

		if (oldLevel > level && level > 0 || event.getMethod() == EquipMethod.JOIN && level > 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			GungHoAttributeEvent gungHo = new GungHoAttributeEvent(human, false, modifier);
			Bukkit.getPluginManager().callEvent(gungHo);
			if (!gungHo.isCancelled()) {
				try {
					addModifier(human, modifier, new String[0]);
					if (hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier)) instance.removeModifier(modifier);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				modifier = result.getModifier();
				gungHo = new GungHoAttributeEvent(human, true, result.getModifier());
				Bukkit.getPluginManager().callEvent(gungHo);
				if (!gungHo.isCancelled()) {
					modifier = gungHo.getModifier();
					addModifier(human, modifier, new String[0]);
					DamageNMS.updateHealth(human);
					return new HealthChangeResult(level, modifier);
				}
			}
		} else if (level > oldLevel) {
			AttributeModifier modifier = result.getModifier();
			GungHoAttributeEvent gungHo = new GungHoAttributeEvent(human, true, result.getModifier());
			Bukkit.getPluginManager().callEvent(gungHo);
			if (!gungHo.isCancelled()) {
				modifier = gungHo.getModifier();
				addModifier(human, modifier, new String[0]);
				DamageNMS.updateHealth(human);
				return new HealthChangeResult(level, modifier);
			}
		} else if (oldLevel > 0 && level == 0) {
			AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation());
			GungHoAttributeEvent gungHo = new GungHoAttributeEvent(human, false, modifier);
			Bukkit.getPluginManager().callEvent(gungHo);
			if (!gungHo.isCancelled()) {
				try {
					addModifier(human, modifier, new String[0]);
					if (hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier)) instance.removeModifier(modifier);
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
