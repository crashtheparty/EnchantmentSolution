package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import org.bukkit.Material;

public enum GoldDiggerCrop {
	WHEAT(Material.WHEAT, 2), CARROTS(Material.CARROTS, 2), POTATOES(Material.POTATOES, 2), BEETROOTS(Material.BEETROOTS, 2),
	NETHER_WARTS(Material.NETHER_WART, 3);
	
	private Material material;
	private int exp;
	
	private GoldDiggerCrop(Material material, int exp) {
		this.material = material;
		this.exp = exp;
	}

	public Material getMaterial() {
		return material;
	}

	public int getExp() {
		return exp;
	}
	
	public static int getExp(Material material, int level) {
		for(GoldDiggerCrop value : values()) {
			if(value.getMaterial() == material) {
				return level * value.getExp();
			}
		}
		return 0;
	}
}
