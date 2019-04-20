package org.ctp.enchantmentsolution.enchantments.helper;

public enum Weight {
	
	COMMON(50), UNCOMMON(25), RARE(10), VERY_RARE(5), EPIC(2), LEGENDARY(1), NULL(0);
	
	private int weight, book, item;
	private String name;
	
	private Weight(int weight) {
		this.weight = weight;
		
		switch(weight) {
		case 50:
			book = 1;
			item = 1;
			name = "common";
			break;
		case 25:
			book = 2;
			item = 1;
			name = "uncommon";
			break;
		case 10:
			book = 4;
			item = 2;
			name = "rare";
			break;
		case 5:
			book = 6;
			item = 3;
			name = "very_rare";
			break;
		case 2:
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
		if(name == null) return NULL;
		for(Weight weight : Weight.values()) {
			if(name.toLowerCase().equals(weight.getName())) {
				return weight;
			}
		}
		return NULL;
	}
	
}
