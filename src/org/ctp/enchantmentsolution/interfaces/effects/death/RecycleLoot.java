package org.ctp.enchantmentsolution.interfaces.effects.death;

import org.bukkit.Material;
import org.ctp.crashapi.item.MatData;

public class RecycleLoot {

	private MatData matData;
	private int minExp, maxExp;

	public RecycleLoot(String material, int minExp, int maxExp) {
		matData = new MatData(material);
		this.minExp = minExp;
		this.maxExp = maxExp;
	}

	public String getMaterialString() {
		return matData.getMaterialName();
	}

	public Material getMaterial() {
		return matData.getMaterial();
	}

	public int getMinExp() {
		return minExp;
	}

	public int getMaxExp() {
		return maxExp;
	}

	public int getRandomExp(int amount) {
		int exp = 0;
		while (amount > 0) {
			exp += getRandomExp();
			amount--;
		}
		return exp;
	}

	public int getRandomExp() {
		int rand = (int) (Math.random() * (maxExp - minExp + 1) + minExp);
		return rand > 0 ? rand : 0;
	}
}