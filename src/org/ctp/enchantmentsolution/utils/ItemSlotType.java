package org.ctp.enchantmentsolution.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Arnah
 * @since Jul 30, 2015
 */
public enum ItemSlotType {
	MAIN_HAND(45), OFF_HAND(45), HELMET(5), CHESTPLATE(6), LEGGINGS(7), BOOTS(8);

	private final int slot;

	ItemSlotType(int slot) {
		this.slot = slot;
	}

	public final static ItemSlotType matchArmorType(final ItemStack itemStack) {
		if (itemStack == null || itemStack.getType().equals(Material.AIR)) return null;
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
}