package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class TelepathyEvent extends ESBlockBreakEvent {

	private Collection<ItemStack> drops;
	private final TelepathyType type;

	public TelepathyEvent(Block block, Player player, Collection<ItemStack> drops, TelepathyType type) {
		super(block, player, new EnchantmentLevel(CERegister.TELEPATHY, 1));
		this.drops = drops;
		this.type = type;
	}

	public Collection<ItemStack> getDrops() {
		return drops;
	}

	public TelepathyType getType() {
		return type;
	}

	public enum TelepathyType{
		SHULKER_BOX(), CONTAINER(), NORMAL();
	}

}
