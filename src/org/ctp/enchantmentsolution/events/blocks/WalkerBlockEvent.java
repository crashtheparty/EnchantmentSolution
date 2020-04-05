package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class WalkerBlockEvent extends ESBlockPlaceEvent {

	public WalkerBlockEvent(Block placedBlock, BlockState replacedBlockState, Player thePlayer,
	EnchantmentLevel enchantment) {
		super(placedBlock, replacedBlockState, placedBlock.getRelative(BlockFace.DOWN), new ItemStack(Material.AIR), thePlayer, true, EquipmentSlot.FEET, enchantment);
	}

}
