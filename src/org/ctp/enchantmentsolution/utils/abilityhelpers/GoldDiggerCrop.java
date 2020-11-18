package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Material;
import org.ctp.crashapi.item.MatData;

public enum GoldDiggerCrop {
	WHEAT(2), CARROTS(2), POTATOES(2), BEETROOTS(2), NETHER_WARTS(3);

	private MatData material;
	private int exp;

	private GoldDiggerCrop(int exp) {
		material = new MatData(name());
		this.exp = exp;
	}

	public Material getMaterial() {
		return material.getMaterial();
	}

	public int getExp() {
		return exp;
	}

	public static int getExp(Material material, int level) {
		for(GoldDiggerCrop value: values())
			if (value.getMaterial() == material) return level * value.getExp();
		return 0;
	}
}
