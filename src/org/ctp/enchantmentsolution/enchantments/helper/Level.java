package org.ctp.enchantmentsolution.enchantments.helper;

public class Level {

	private int slot, level;
	
	public Level(int slot, int level) {
		this.setSlot(slot);
		this.setLevel(level);
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
