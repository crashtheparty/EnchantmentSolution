package org.ctp.enchantmentsolution.enchantments;

public enum Weight {
	
	COMMON(10), UNCOMMON(5), RARE(2), VERY_RARE(1), NULL(0);
	
	private int weight, book, item;
	private String name;
	
	private Weight(int weight) {
		this.weight = weight;
		
		switch(weight) {
		case 10:
			book = 1;
			item = 1;
			name = "common";
			break;
		case 5:
			book = 2;
			item = 1;
			name = "uncommon";
			break;
		case 2:
			book = 4;
			item = 2;
			name = "rare";
			break;
		case 1:
			book = 8;
			item = 4;
			name = "very_rare";
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
		for(Weight weight : Weight.values()) {
			if(name.toLowerCase().equals(weight.getName())) {
				return weight;
			}
		}
		return NULL;
	}
	
}
