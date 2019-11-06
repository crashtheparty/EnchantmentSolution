package org.ctp.enchantmentsolution.advancements.shared;

/**
 * Specifies a world type.
 * 
 * @see LocationObject
 */
enum Dimension implements SharedEnum {
	OVERWORLD, THE_NETHER, THE_END;

	@Override
	public String getValue() {
		return name().toLowerCase();
	}
}
