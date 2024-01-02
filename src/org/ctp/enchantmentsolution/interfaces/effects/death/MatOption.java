package org.ctp.enchantmentsolution.interfaces.effects.death;

import org.bukkit.Material;
import org.ctp.crashapi.item.MatData;

public class MatOption {
	private MatData material;
	private int chance, min, max;

	public MatOption(String material, int chance, int min, int max) {
		setMaterial(new MatData(material));
		setChance(chance);
		setMin(min);
		setMax(max);
	}

	public Material getMaterial() {
		return material.getMaterial();
	}

	public void setMaterial(MatData material) {
		this.material = material;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
