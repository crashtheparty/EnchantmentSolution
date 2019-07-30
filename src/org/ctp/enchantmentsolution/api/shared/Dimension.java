package org.ctp.enchantmentsolution.api.shared;

/**
 * Specifies a world type.
 * @see LocationObject
 */
@SuppressWarnings("unused") enum Dimension implements SharedEnum {
	OVERWORLD,
	THE_NETHER,
	THE_END;
	
	@Override
	public String getValue() {
		return name().toLowerCase();
	}
}
