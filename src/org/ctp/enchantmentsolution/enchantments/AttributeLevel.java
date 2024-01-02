package org.ctp.enchantmentsolution.enchantments;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

public class AttributeLevel {

	private final Attribute attr;
	private final AttributeModifier attribute;

	public AttributeLevel(Attribute attr, AttributeModifier attribute) {
		this.attribute = attribute;
		this.attr = attr;
	}

	public AttributeModifier getAttribute() {
		return attribute;
	}

	public Attribute getAttr() {
		return attr;
	}
}
