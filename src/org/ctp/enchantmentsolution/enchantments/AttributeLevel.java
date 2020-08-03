package org.ctp.enchantmentsolution.enchantments;

import org.ctp.enchantmentsolution.utils.abilityhelpers.ItemEquippedSlot;

public class AttributeLevel {

	private final Attributable attribute;
	private final int level;
	private final ItemEquippedSlot slot;

	public AttributeLevel(Attributable attribute, int level, ItemEquippedSlot slot) {
		this.attribute = attribute;
		this.level = level;
		this.slot = slot;
	}

	public Attributable getAttribute() {
		return attribute;
	}

	public int getLevel() {
		return level;
	}

	public ItemEquippedSlot getSlot() {
		return slot;
	}
}
