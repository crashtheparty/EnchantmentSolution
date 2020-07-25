package org.ctp.enchantmentsolution.crashapi.item;

import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * @author Arnah
 * @since Jul 30, 2015
 */
public enum ItemSlotType {
	MAIN_HAND(45, EquipmentSlot.HAND), OFF_HAND(45, EquipmentSlot.OFF_HAND), HELMET(5, EquipmentSlot.HEAD), CHESTPLATE(6, EquipmentSlot.CHEST),
	LEGGINGS(7, EquipmentSlot.LEGS), BOOTS(8, EquipmentSlot.FEET);

	public static List<ItemSlotType> ARMOR = Arrays.asList(HELMET, CHESTPLATE, LEGGINGS, BOOTS);
	private final int slot;
	private final EquipmentSlot equipmentSlot;

	ItemSlotType(int slot, EquipmentSlot equipmentSlot) {
		this.slot = slot;
		this.equipmentSlot = equipmentSlot;
	}

	public final static ItemSlotType matchArmorType(final ItemStack itemStack) {
		if (itemStack == null || MatData.isAir(itemStack.getType())) return null;
		String type = itemStack.getType().name();
		if (type.endsWith("_HELMET") || type.endsWith("_SKULL")) return HELMET;
		else if (type.endsWith("_CHESTPLATE") || type.contains("ELYTRA")) return CHESTPLATE;
		else if (type.endsWith("_LEGGINGS")) return LEGGINGS;
		else if (type.endsWith("_BOOTS")) return BOOTS;
		else
			return null;
	}

	public int getSlot() {
		return slot;
	}

	public EquipmentSlot getEquipmentSlot() {
		return equipmentSlot;
	}
}