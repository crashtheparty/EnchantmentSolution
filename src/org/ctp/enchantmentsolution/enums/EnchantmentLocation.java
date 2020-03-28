package org.ctp.enchantmentsolution.enums;

import java.util.Arrays;
import java.util.List;

public enum EnchantmentLocation {
	
	TABLE(), NON_BOOK(), CHEST_LOOT(), MOB_LOOT(), FISHING_LOOT(), VILLAGER_TRADES(), PIGLIN_TRADES(), NONE();
	
	public static List<EnchantmentLocation> getDefaultLocations() {
		return Arrays.asList(TABLE, NON_BOOK, CHEST_LOOT, MOB_LOOT, FISHING_LOOT, VILLAGER_TRADES);
	}
	
	public static List<EnchantmentLocation> getNoBooksLocations() {
		return Arrays.asList(TABLE, CHEST_LOOT, MOB_LOOT, FISHING_LOOT, VILLAGER_TRADES);
	}

	public static List<EnchantmentLocation> getTreasureLocations() {
		return Arrays.asList(CHEST_LOOT, NON_BOOK, MOB_LOOT, FISHING_LOOT, VILLAGER_TRADES);
	}
	
	public static List<EnchantmentLocation> getNoBooksTreasureLocations() {
		return Arrays.asList(CHEST_LOOT, MOB_LOOT, FISHING_LOOT, VILLAGER_TRADES);
	}
}
