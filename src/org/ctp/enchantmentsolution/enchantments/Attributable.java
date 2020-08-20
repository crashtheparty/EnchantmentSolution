package org.ctp.enchantmentsolution.enchantments;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.ctp.crashapi.item.ItemSlotType;
import org.ctp.crashapi.nms.DamageEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.AttributeEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ItemEquippedSlot;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public enum Attributable {
	ARMORED(RegisterEnchantments.ARMORED, Attribute.GENERIC_ARMOR, ItemEquippedSlot.getArmorTypes("armored_armor", ItemEquippedSlot.ARMORED_ID_BASE), UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"), false, Operation.ADD_NUMBER, null),
	GUNG_HO(RegisterEnchantments.GUNG_HO, Attribute.GENERIC_MAX_HEALTH, ItemEquippedSlot.getArmorTypes("gung_ho_health", ItemEquippedSlot.GUNG_HO_ID_BASE), UUID.fromString("eeeeeeee-ffff-ffff-ffff-000000000000"), false, Operation.ADD_NUMBER, "generic.maxHealth"),
	LIFE(RegisterEnchantments.LIFE, Attribute.GENERIC_MAX_HEALTH, ItemEquippedSlot.getArmorTypes(RegisterEnchantments.LIFE, UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000001000"), UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000001100"), UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000001110"), UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000001111")), UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), true, Operation.ADD_NUMBER, "life_health"),
	QUICK_STRIKE(RegisterEnchantments.QUICK_STRIKE, Attribute.GENERIC_ATTACK_SPEED, Arrays.asList(new ItemEquippedSlot(ItemSlotType.MAIN_HAND, "quick_strike_speed", UUID.fromString("dddddddd-fefe-fefe-fefe-000000000000"))), UUID.fromString("dddddddd-fefe-fefe-fefe-000000000000"), false, Operation.ADD_SCALAR, "quick_strike_armor"),
	TOUGHNESS(RegisterEnchantments.TOUGHNESS, Attribute.GENERIC_ARMOR_TOUGHNESS, ItemEquippedSlot.getArmorTypes(RegisterEnchantments.ARMORED, UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001000"), UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001100"), UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001110"), UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001111")), true, Operation.ADD_NUMBER);

	private final Attribute attr;
	private final List<ItemEquippedSlot> types;
	private final boolean allowMultiple;
	private final String legacyAttrName;
	private final UUID legacyUUID;
	private final Operation operation;
	private final Enchantment enchantment;

	Attributable(Enchantment enchantment, Attribute attr, List<ItemEquippedSlot> types, boolean allowMultiple, Operation operation) {
		this(enchantment, attr, types, null, allowMultiple, operation, null);
	}

	Attributable(Enchantment enchantment, Attribute attr, List<ItemEquippedSlot> types, UUID legacyUUID, boolean allowMultiple, Operation operation,
	String legacyAttrName) {
		this.enchantment = enchantment;
		this.attr = attr;
		this.types = types;
		this.legacyUUID = legacyUUID;
		this.allowMultiple = allowMultiple;
		this.operation = operation;
		this.legacyAttrName = legacyAttrName;
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
			case "TOUGHNESS":
				return level;
		}
		return 0;
	}

	public Operation getOperation() {
		return operation;
	}

	public String getLegacyAttrName() {
		return legacyAttrName;
	}

	public Attribute getAttr() {
		return attr;
	}

	public boolean hasAttribute(Player player, ItemSlotType type) {
		return hasAttribute(player, type, false);
	}

	public boolean hasAttribute(Player player, ItemSlotType type, boolean legacy) {
		AttributeInstance instance = player.getAttribute(getAttr());
		if (instance == null) return false;
		UUID id = null;
		String name = null;
		ItemEquippedSlot s = null;
		for(ItemEquippedSlot slot: types)
			if (type == slot.getType()) s = slot;
		if (s == null) return false;
		if (legacy) {
			id = legacyUUID;
			name = s.getName();
		} else {
			id = s.getUuid();
			name = s.getName();
		}
		AttributeModifier modifier = new AttributeModifier(id, name, getValue(instance, 0), operation);
		return hasExactAttribute(instance, modifier) || hasAttribute(instance, modifier);
	}

	public void addModifier(Player player, int level, ItemSlotType type) {
		AttributeInstance instance = player.getAttribute(getAttr());
		if (instance == null) return;
		ItemEquippedSlot s = null;
		for(ItemEquippedSlot slot: types)
			if (type == slot.getType()) s = slot;
		if (s == null) return;
		AttributeModifier modifier = new AttributeModifier(s.getUuid(), s.getName(), getValue(instance, level), operation, type.getEquipmentSlot());
		try {
			if (!hasExactAttribute(instance, modifier) && hasAttribute(instance, modifier)) {
				List<AttributeModifier> override = new ArrayList<AttributeModifier>();
				for(AttributeModifier m: instance.getModifiers())
					if (m.getUniqueId().equals(modifier.getUniqueId())) override.add(m);
				for(AttributeModifier m: override)
					instance.getModifiers().remove(m);
				if (modifier.getName().equals("armored_armor")) AdvancementUtils.awardCriteria(player, ESAdvancement.ARMORED_EVOLUTION, "armored");
				instance.addModifier(modifier);
			} else if (!hasAttribute(instance, modifier)) {
				if (modifier.getName().equals("armored_armor")) AdvancementUtils.awardCriteria(player, ESAdvancement.ARMORED_EVOLUTION, "armored");
				instance.addModifier(modifier);
			}
		} catch (Exception ex) {}
	}

	public void removeModifier(Player player, ItemSlotType type, boolean legacy) {
		ItemEquippedSlot s = null;
		for(ItemEquippedSlot slot: types)
			if (slot.getType() == type) s = slot;
		if (s != null && legacy) remove(player, s.getName(), legacyUUID);
		else if (s != null) remove(player, s.getName(), s.getUuid());
		if (legacyAttrName != null) remove(player, legacyAttrName, legacyUUID);
	}

	private void remove(Player player, String attrName, UUID uuid) {
		AttributeInstance instance = player.getAttribute(getAttr());
		AttributeModifier modifier = new AttributeModifier(uuid, attrName, 0, operation);
		try {
			if (hasExactAttribute(instance, modifier)) instance.removeModifier(modifier);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean hasExactAttribute(AttributeInstance instance, AttributeModifier modifier) {
		for(AttributeModifier m: instance.getModifiers())
			if (m.getUniqueId().equals(modifier.getUniqueId()) && m.getName().equals(modifier.getName())) return true;
		return false;
	}

	private boolean hasAttribute(AttributeInstance instance, AttributeModifier modifier) {
		for(AttributeModifier m: instance.getModifiers())
			if (m.getUniqueId().equals(modifier.getUniqueId())) return true;
		return false;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public List<ItemEquippedSlot> getTypes() {
		return types;
	}

	public boolean doesAllowMultiple() {
		return allowMultiple;
	}

	public UUID getLegacyUUID() {
		return legacyUUID;
	}

	public static void addAttribute(Player player, EnchantmentLevel level, Attributable a, ItemEquippedSlot slot) {
		AttributeEvent attrEvent = new AttributeEvent(player, level, null, slot.getName());
		Bukkit.getPluginManager().callEvent(attrEvent);

		a.addModifier(player, level.getLevel(), slot.getType());
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		esPlayer.addAttribute(new AttributeLevel(a, level.getLevel(), slot));
		if (a.getEnchantment() == RegisterEnchantments.TOUGHNESS && player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue() >= 20) AdvancementUtils.awardCriteria(attrEvent.getPlayer(), ESAdvancement.GRAPHENE_ARMOR, "toughness");
		if (a.getEnchantment() == RegisterEnchantments.LIFE || a.getEnchantment() == RegisterEnchantments.GUNG_HO) DamageEvent.updateHealth(player);
	}

	public static void removeAttribute(Player player, EnchantmentLevel level, Attributable a, ItemEquippedSlot slot) {
		removeAttribute(player, level, a, slot, false);
	}

	public static void removeAttribute(Player player, EnchantmentLevel level, Attributable a, ItemEquippedSlot slot, boolean legacy) {
		AttributeEvent attrEvent = new AttributeEvent(player, level, slot.getName(), null);
		Bukkit.getPluginManager().callEvent(attrEvent);

		a.removeModifier(player, slot.getType(), legacy);
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		esPlayer.removeAttribute(a, slot);
		if (a.getEnchantment() == RegisterEnchantments.LIFE || a.getEnchantment() == RegisterEnchantments.GUNG_HO) DamageEvent.updateHealth(player);

	}
}
