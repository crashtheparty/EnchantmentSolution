package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.DamageState;

public class WalkerBlock {
	private final Enchantment enchantment;
	private final Material replaceType;
	private final Block block;
	private final int tick;
	private DamageState damage;
	private final String meta;

	public WalkerBlock(Enchantment enchantment, Block block, Material replaceType, int tick) {
		this.block = block;
		this.tick = tick;
		this.enchantment = enchantment;
		this.replaceType = replaceType;
		damage = DamageState.NORMAL;
		if (enchantment == RegisterEnchantments.MAGMA_WALKER) meta = "MagmaWalker";
		else if (enchantment == RegisterEnchantments.VOID_WALKER) meta = "VoidWalker";
		else
			meta = "";
	}

	public WalkerBlock(Enchantment enchantment, Block block, Material replaceType, int tick, DamageState damage) {
		this(enchantment, block, replaceType, tick);
		this.damage = damage;
	}

	public Block getBlock() {
		return block;
	}

	public int getTick() {
		return tick;
	}

	public Enchantment getEnchantment() {
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
