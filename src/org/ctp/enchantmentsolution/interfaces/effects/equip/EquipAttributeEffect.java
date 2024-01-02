package org.ctp.enchantmentsolution.interfaces.effects.equip;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventPriority;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.AttributeLevel;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityEquipEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public abstract class EquipAttributeEffect extends EntityEquipEffect {

	private final Attribute attr;
	private final UUID attrUUID;
	private final String attrName;
	private final Operation operation;

	public EquipAttributeEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	Attribute attr, UUID attrUUID, String attrName, Operation operation, EquipCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.attr = attr;
		this.attrUUID = attrUUID;
		this.attrName = attrName;
		this.operation = operation;
	}

	public UUID getAttrUUID() {
		return attrUUID;
	}

	public String getAttrName() {
		return attrName;
	}

	public Operation getOperation() {
		return operation;
	}

	public Attribute getAttr() {
		return attr;
	}

	public void addModifier(HumanEntity player, AttributeModifier modifier, String[] legacy) {
		ESPlayer es = player instanceof OfflinePlayer ? EnchantmentSolution.getESPlayer((OfflinePlayer) player) : null;
		AttributeInstance instance = player.getAttribute(getAttr());
		try {
			List<AttributeModifier> override = new ArrayList<AttributeModifier>();
			for(AttributeModifier m: instance.getModifiers())
				if (m != null && m.getUniqueId().equals(modifier.getUniqueId())) override.add(m);
			for(AttributeModifier m: override) {
				instance.removeModifier(m);
				if (es != null) es.removeAttribute(m);
			}
			if (modifier.getAmount() != 0) {
				instance.addModifier(modifier);
				if (es != null) {
					es.addAttribute(new AttributeLevel(getAttr(), modifier));
					if (modifier.getName().equals("armored_armor")) AdvancementUtils.awardCriteria(es.getOnlinePlayer(), ESAdvancement.ARMORED_EVOLUTION, "armored");
				}
			} else if (es != null) es.removeAttribute(modifier);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean hasExactAttribute(AttributeInstance instance, AttributeModifier modifier) {
		for(AttributeModifier m: instance.getModifiers())
			if (m != null && m.getUniqueId().equals(modifier.getUniqueId()) && m.getName().equals(modifier.getName())) return true;
		return false;
	}

	public boolean hasAttribute(AttributeInstance instance, AttributeModifier modifier) {
		for(AttributeModifier m: instance.getModifiers())
			if (m.getUniqueId().equals(modifier.getUniqueId()) || m.getName().equals(modifier.getName())) return true;
		return false;
	}

	public boolean hasAttribute(AttributeInstance instance, AttributeModifier modifier, String... legacy) {
		for(AttributeModifier m: instance.getModifiers()) {
			if (m.getUniqueId().equals(modifier.getUniqueId()) || m.getName().equals(modifier.getName())) return true;
			for(String s: legacy)
				if (m.getName().equals(s)) return true;
		}
		return false;
	}

}
