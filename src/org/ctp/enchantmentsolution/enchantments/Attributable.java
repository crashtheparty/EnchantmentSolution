package org.ctp.enchantmentsolution.enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ItemSlotType;

public enum Attributable {
	ARMORED(RegisterEnchantments.ARMORED, Attribute.GENERIC_ARMOR, ItemSlotType.CHESTPLATE, "armored_armor",
	UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"),
	Operation.ADD_NUMBER), GUNG_HO(RegisterEnchantments.GUNG_HO, Attribute.GENERIC_MAX_HEALTH,
	ItemSlotType.CHESTPLATE, "gung_ho_health", UUID.fromString("eeeeeeee-ffff-ffff-ffff-000000000000"),
	Operation.ADD_NUMBER, "generic.maxHealth"), LIFE(RegisterEnchantments.LIFE,
	Attribute.GENERIC_MAX_HEALTH, ItemSlotType.CHESTPLATE, "life_health",
	UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), Operation.ADD_NUMBER,
	"generic.maxHealth"), QUICK_STRIKE(RegisterEnchantments.QUICK_STRIKE,
	Attribute.GENERIC_ATTACK_SPEED, ItemSlotType.MAIN_HAND, "quick_strike_armor",
	UUID.fromString("dddddddd-fefe-fefe-fefe-000000000000"),
	Operation.ADD_SCALAR), TOUGHNESS_HELMET(RegisterEnchantments.TOUGHNESS,
	Attribute.GENERIC_ARMOR_TOUGHNESS, ItemSlotType.HELMET, "helmet_toughness",
	UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001000"),
	Operation.ADD_NUMBER), TOUGHNESS_CHESTPLATE(RegisterEnchantments.TOUGHNESS,
	Attribute.GENERIC_ARMOR_TOUGHNESS, ItemSlotType.CHESTPLATE,
	"chestplate_toughness",
	UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001100"),
	Operation.ADD_NUMBER), TOUGHNESS_LEGGINGS(
	RegisterEnchantments.TOUGHNESS,
	Attribute.GENERIC_ARMOR_TOUGHNESS, ItemSlotType.LEGGINGS,
	"leggings_toughness",
	UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001110"),
	Operation.ADD_NUMBER), TOUGHNESS_BOOTS(
	RegisterEnchantments.TOUGHNESS,
	Attribute.GENERIC_ARMOR_TOUGHNESS,
	ItemSlotType.BOOTS, "boots_toughness",
	UUID.fromString(
	"bbbbbbbb-fefe-fefe-fefe-000000001111"),
	Operation.ADD_NUMBER);

	private Attribute attr;
	private ItemSlotType type;
	private String attrName, legacyAttrName;
	private UUID uuid;
	private Operation operation;
	private Enchantment enchantment;

	Attributable(Enchantment enchantment, Attribute attr, ItemSlotType type, String attrName, UUID uuid,
	Operation operation) {
		this(enchantment, attr, type, attrName, uuid, operation, null);
	}

	Attributable(Enchantment enchantment, Attribute attr, ItemSlotType type, String attrName, UUID uuid,
	Operation operation, String legacyAttrName) {
		setEnchantment(enchantment);
		setAttr(attr);
		setType(type);
		setAttrName(attrName);
		setUuid(uuid);
		setOperation(operation);
		setLegacyAttrName(legacyAttrName);
	}

	public double getValue(AttributeInstance a, int level) {
		switch (name()) {
			case "ARMORED":
				return 2 * level;
			case "GUNG_HO":
				return -1 * a.getDefaultValue() / 2;
			case "LIFE":
				return 4 * level;
			case "QUICK_STRIKE":
				return 0.5 * level;
			case "TOUGHNESS_HELMET":
			case "TOUGHNESS_CHESTPLATE":
			case "TOUGHNESS_LEGGINGS":
			case "TOUGHNESS_BOOTS":
				return level;
		}
		return 0;
	}

	public String getAttrName() {
		return attrName;
	}

	protected void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public UUID getUuid() {
		return uuid;
	}

	protected void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Operation getOperation() {
		return operation;
	}

	protected void setOperation(Operation operation) {
		this.operation = operation;
	}

	public String getLegacyAttrName() {
		return legacyAttrName;
	}

	public void setLegacyAttrName(String legacyAttrName) {
		this.legacyAttrName = legacyAttrName;
	}

	public Attribute getAttr() {
		return attr;
	}

	public void setAttr(Attribute attr) {
		this.attr = attr;
	}

	public boolean hasAttribute(Player player) {
		AttributeInstance instance = player.getAttribute(getAttr());
		AttributeModifier modifier = new AttributeModifier(uuid, attrName, getValue(instance, 0), operation);

		return hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier);
	}

	public void addModifier(Player player, int level) {
		AttributeInstance instance = player.getAttribute(getAttr());
		AttributeModifier modifier = new AttributeModifier(uuid, attrName, getValue(instance, level), operation);
		try {
			if (!hasExactAttribute(instance, modifier) && hasAttribute(instance, modifier)) {
				List<AttributeModifier> override = new ArrayList<AttributeModifier>();
				for(AttributeModifier m: instance.getModifiers()) {
					if (m.getUniqueId().equals(modifier.getUniqueId())) {
						override.add(m);
					}
				}
				for(AttributeModifier m: override) {
					AttributeModifier newModifier = new AttributeModifier(UUID.randomUUID(), m.getName(), m.getAmount(),
					m.getOperation(), m.getSlot());
					while (hasAttribute(instance, newModifier)) {
						newModifier = new AttributeModifier(UUID.randomUUID(), m.getName(), m.getAmount(),
						m.getOperation(), m.getSlot());
					}
					instance.getModifiers().remove(m);
					instance.getModifiers().add(newModifier);
				}
				if (modifier.getName().equals("armored_armor")) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.ARMORED_EVOLUTION, "armored");
				}
				instance.addModifier(modifier);
			} else if (!hasAttribute(instance, modifier)) {
				if (modifier.getName().equals("armored_armor")) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.ARMORED_EVOLUTION, "armored");
				}
				instance.addModifier(modifier);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void removeModifier(Player player) {
		remove(player, attrName);
		if (legacyAttrName != null) {
			remove(player, attrName);
		}
	}

	private void remove(Player player, String attrName) {
		AttributeInstance instance = player.getAttribute(getAttr());
		AttributeModifier modifier = new AttributeModifier(uuid, attrName, 0, operation);
		try {
			if (hasExactAttribute(instance, modifier)) {
				instance.removeModifier(modifier);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean hasExactAttribute(AttributeInstance instance, AttributeModifier modifier) {
		for(AttributeModifier m: instance.getModifiers()) {
			if (m.getUniqueId().equals(modifier.getUniqueId()) && m.getName().equals(modifier.getName())) {
				return true;
			}
		}
		return false;
	}

	private boolean hasAttribute(AttributeInstance instance, AttributeModifier modifier) {
		for(AttributeModifier m: instance.getModifiers()) {
			if (m.getUniqueId().equals(modifier.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	public ItemSlotType getType() {
		return type;
	}

	public void setType(ItemSlotType type) {
		this.type = type;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(Enchantment enchantment) {
		this.enchantment = enchantment;
	}
}
