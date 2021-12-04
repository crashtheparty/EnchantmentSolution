package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class FlashUpdateEvent extends ESBlockPhysicsEvent {

	private final int oldLightLevel;
	private int lightLevel;
	
	public FlashUpdateEvent(Block theBlock, BlockData changed, Player player, int oldLightLevel, int lightLevel) {
		super(theBlock, changed, player, new EnchantmentLevel(CERegister.FLASH, 1));
		this.oldLightLevel = oldLightLevel;
		this.setLightLevel(lightLevel);
	}

	public int getOldLightLevel() {
		return oldLightLevel;
	}

	public int getLightLevel() {
		return lightLevel;
	}

	public void setLightLevel(int lightLevel) {
		this.lightLevel = lightLevel;
	}

}
