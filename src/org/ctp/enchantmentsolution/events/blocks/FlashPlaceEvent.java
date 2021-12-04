package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class FlashPlaceEvent extends ESBlockPlaceEvent {

	private int lightLevel;
	
	public FlashPlaceEvent(Block placedBlock, BlockState replacedBlockState, Block placedAgainst, ItemStack itemInHand, Player thePlayer, boolean canBuild,
	EquipmentSlot hand, int lightLevel) {
		super(placedBlock, replacedBlockState, placedAgainst, itemInHand, thePlayer, canBuild, hand, new EnchantmentLevel(CERegister.FLASH, 1));
		this.setLightLevel(lightLevel);
	}

	public int getLightLevel() {
		return lightLevel;
	}

	public void setLightLevel(int lightLevel) {
		this.lightLevel = lightLevel;
	}

}