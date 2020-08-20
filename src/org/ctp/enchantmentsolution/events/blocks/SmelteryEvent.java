package org.ctp.enchantmentsolution.events.blocks;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class SmelteryEvent extends ESBlockDropAddItemEvent {

	private final ItemStack drop;
	private Material changeTo;
	private boolean fortune;
	private int exp;

	public SmelteryEvent(Block block, BlockData blockData, Player player, ItemStack drop, Material changeTo, int exp, boolean fortune) {
		super(block, blockData, new EnchantmentLevel(CERegister.SMELTERY, 1), player, Arrays.asList(drop));
		this.drop = drop;
		setChangeTo(changeTo);
		setExp(exp);
		setFortune(fortune);
	}

	public Material getChangeTo() {
		return changeTo;
	}

	public void setChangeTo(Material changeTo) {
		this.changeTo = changeTo;
	}

	public boolean willFortune() {
		return fortune;
	}

	public void setFortune(boolean fortune) {
		this.fortune = fortune;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public ItemStack getDrop() {
		return drop;
	}
}
