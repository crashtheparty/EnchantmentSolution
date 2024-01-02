package org.ctp.enchantmentsolution.enchantments.helper;

import org.ctp.crashapi.item.ItemData;

public class Seed {

	private ItemData data;
	private int seed;
	
	public Seed(ItemData data, int seed) {
		this.setData(data);
		this.setSeed(seed);
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	public long getFinalSeed(int bookshelves) {
		return this.seed * 100 + bookshelves;
	}

	public ItemData getData() {
		return data;
	}

	public void setData(ItemData data) {
		this.data = data;
	}
}
