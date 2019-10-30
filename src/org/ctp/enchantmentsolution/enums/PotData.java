package org.ctp.enchantmentsolution.enums;

import org.bukkit.potion.PotionEffectType;

public class PotData {
	private PotionEffectType potion;
	private String potionName;
	
	public PotData(String name) {
		this.potionName = name.toUpperCase();
		try {
			this.potion = PotionEffectType.getByName(this.potionName);
		} catch (Exception ex) {
		}
	}
	
	public PotionEffectType getPotionEffect() {
		return potion;
	}
	
	public String getPotionName() {
		return potionName;
	}
}
