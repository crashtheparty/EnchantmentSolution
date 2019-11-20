package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SmelteryEvent extends ESBlockBreakEvent {

	private ItemStack drop;
	private Material changeTo;
	private boolean fortune = true;
	private int exp;

	public SmelteryEvent(Block block, Player player, ItemStack drop, Material changeTo, int exp) {
		super(block, player);
		setDrop(drop);
		setChangeTo(changeTo);
		setExp(exp);
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

	public void setDrop(ItemStack drop) {
		this.drop = drop;
	}

}
