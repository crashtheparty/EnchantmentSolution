package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.Locale;

public enum Weight {

	COMMON(300), UNCOMMON(100), RARE(30), VERY_RARE(10), EPIC(3), LEGENDARY(1), NULL(0);

	private int weight, book, item;
	private String name;

	private Weight(int weight) {
		this.weight = weight;

		switch (weight) {
			case 300:
				book = 1;
				item = 1;
				name = "common";
				break;
			case 100:
				book = 2;
				item = 1;
				name = "uncommon";
				break;
			case 30:
				book = 4;
				item = 2;
				name = "rare";
				break;
			case 10:
				book = 6;
				item = 3;
				name = "very_rare";
				break;
			case 3:
				book = 8;
				item = 4;
				name = "epic";
				break;
			case 1:
				book = 12;
				item = 6;
				name = "legendary";
				break;
			case 0:
				book = 0;
				item = 0;
				name = "null";
				break;
		}
	}

	public int getWeight() {
		return weight;
	}

	public int getBook() {
		return book;
	}

	public int getItem() {
		return item;
	}

	public String getName() {
		return name;
	}

	public static Weight getWeight(String name) {
		if (name == null) return NULL;
		for(Weight weight: Weight.values())
			if (name.toLowerCase(Locale.ROOT).equals(weight.getName())) return weight;
		return NULL;
	}

}
