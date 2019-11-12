package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.metadata.FixedMetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public enum DamageState {
	NORMAL(10, 4), MINIMAL(2, 3), LOW(4, 2), HIGH(6, 1), MAXIMAL(8, 0), BREAK(10, -1);

	private final FixedMetadataValue value = new FixedMetadataValue(EnchantmentSolution.getPlugin(), new Integer(4));
	private int damage, stage;

	DamageState(int damage, int stage) {
		setDamage(damage);
		setStage(stage);
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public FixedMetadataValue getValue() {
		return value;
	}

	public static int getStage(int asInt) {
		return 0;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public static FixedMetadataValue getDefaultMeta() {
		return NORMAL.value;
	}
}
