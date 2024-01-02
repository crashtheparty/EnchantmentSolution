package org.ctp.enchantmentsolution.enums;

public enum EnchantmentLocation {

	TABLE(), CHEST_LOOT(), MOB_LOOT(), FISHING_LOOT(), VILLAGER(), END_CITY(), PIGLIN(), DEEP_DARK(), NONE();

	public static EnchantmentLocation[] getDefaultLocations() {
		return new EnchantmentLocation[] { TABLE, CHEST_LOOT, MOB_LOOT, FISHING_LOOT, VILLAGER, DEEP_DARK, END_CITY, PIGLIN };
	}

	public static EnchantmentLocation[] getCurseLocations() {
		return new EnchantmentLocation[] { CHEST_LOOT, MOB_LOOT, FISHING_LOOT, VILLAGER, DEEP_DARK, END_CITY, PIGLIN };
	}

	public static EnchantmentLocation[] getRareLocations() {
		return new EnchantmentLocation[] { DEEP_DARK, END_CITY, PIGLIN };
	}

}
