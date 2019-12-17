package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockPlaceEvent extends BlockPlaceEvent {

	private final EnchantmentLevel enchantment;
	
	public ESBlockPlaceEvent(Block placedBlock, BlockState replacedBlockState, Block placedAgainst,
	ItemStack itemInHand, Player thePlayer, boolean canBuild, EquipmentSlot hand, EnchantmentLevel enchantment) {
		super(placedBlock, replacedBlockState, placedAgainst, itemInHand, thePlayer, canBuild, hand);
		this.enchantment = enchantment;
	}

	public EnchantmentLevel getEnchantment() {
		return enchantment;
	}

}
