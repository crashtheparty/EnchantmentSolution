package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.events.blocks.DamageState;
import org.ctp.enchantmentsolution.interfaces.WalkerInterface;

public class WalkerBlock {
	private final EnchantmentWrapper enchantment;
	private final Material replaceType;
	private final Block block;
	private final int tick;
	private DamageState damage;
	private final String meta;

	public WalkerBlock(WalkerInterface walker, Block block, int tick) {
		this.block = block;
		this.tick = tick;
		enchantment = walker.getEnchantment();
		replaceType = walker.getReplaceMaterial();
		damage = DamageState.NORMAL;
		meta = walker.getMetadata();
	}

	public WalkerBlock(WalkerInterface walker, Block block, int tick, DamageState damage) {
		this(walker, block, tick);
		this.damage = damage;
	}

	public Block getBlock() {
		return block;
	}

	public int getTick() {
		return tick;
	}

	public EnchantmentWrapper getEnchantment() {
		return enchantment;
	}

	public Material getReplaceType() {
		return replaceType;
	}

	public DamageState getDamage() {
		return damage;
	}

	public DamageState getNextDamage() {
		return DamageState.getState(damage.getStage() - 1);
	}

	public void nextDamage() {
		damage = DamageState.getState(damage.getStage() - 1);
	}

	public String getMeta() {
		return meta;
	}
}
