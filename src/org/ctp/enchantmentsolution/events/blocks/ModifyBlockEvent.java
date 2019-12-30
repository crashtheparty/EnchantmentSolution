package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ModifyBlockEvent extends ESPlayerEvent {

	private Block block;
	
	public ModifyBlockEvent(Player who, EnchantmentLevel enchantment, Block block) {
		super(who, enchantment);
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
}
