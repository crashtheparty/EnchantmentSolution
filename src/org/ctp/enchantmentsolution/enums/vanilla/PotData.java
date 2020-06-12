package org.ctp.enchantmentsolution.enums.vanilla;

import org.bukkit.potion.PotionEffectType;

public class PotData {
	private PotionEffectType potion;
	private String potionName;

	public PotData(String name) {
		potionName = name.toUpperCase();
		try {
			potion = PotionEffectType.getByName(potionName);
		} catch (Exception ex) {}
	}

	public PotionEffectType getPotionEffect() {
		return potion;
	}

	public String getPotionName() {
		return potionName;
	}
}
