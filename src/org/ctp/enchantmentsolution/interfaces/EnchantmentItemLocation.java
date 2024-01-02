package org.ctp.enchantmentsolution.interfaces;

import org.ctp.crashapi.item.ItemSlotType;

public enum EnchantmentItemLocation {
	ATTACKED(ItemSlotType.MAIN_HAND), DEFENDED(ItemSlotType.OFF_HAND), HANDS(ItemSlotType.MAIN_HAND, ItemSlotType.OFF_HAND), BOOTS(ItemSlotType.BOOTS),
	LEGGINGS(ItemSlotType.LEGGINGS), CHESTPLATE(ItemSlotType.CHESTPLATE), HELMET(ItemSlotType.HELMET),
	WEARING(ItemSlotType.HELMET, ItemSlotType.CHESTPLATE, ItemSlotType.LEGGINGS, ItemSlotType.BOOTS),
	EQUIPPED(ItemSlotType.MAIN_HAND, ItemSlotType.OFF_HAND, ItemSlotType.HELMET, ItemSlotType.CHESTPLATE, ItemSlotType.LEGGINGS, ItemSlotType.BOOTS),
	IN_INVENTORY(ItemSlotType.values());

	private ItemSlotType[] types;

	private EnchantmentItemLocation(ItemSlotType... types) {
		this.types = types;
	}

	public ItemSlotType[] getTypes() {
		return types;
	}

}
