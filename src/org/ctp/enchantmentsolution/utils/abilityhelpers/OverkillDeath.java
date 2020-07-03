package org.ctp.enchantmentsolution.utils.abilityhelpers;

public class OverkillDeath {

	private int ticks;

	public OverkillDeath() {
		ticks = 90;
	}

	public int getTicks() {
		return ticks;
	}

	public void minus() {
		ticks--;
	}
}
