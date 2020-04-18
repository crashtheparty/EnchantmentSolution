package org.ctp.enchantmentsolution.enums;

public class CustomItemType extends ItemType {

	private final VanillaItemType vanilla;
	
	public CustomItemType(String type, VanillaItemType vanilla) {
		super(type);
		this.vanilla = vanilla;
	}

	public VanillaItemType getVanilla() {
		return vanilla;
	}

}
