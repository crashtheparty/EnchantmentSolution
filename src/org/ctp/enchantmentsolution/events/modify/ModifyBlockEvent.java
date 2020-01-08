package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ModifyBlockEvent extends ModifyActionEvent {

	private final Block block;
	
	public ModifyBlockEvent(Player who, EnchantmentLevel enchantment, Block block) {
		super(who, enchantment);
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}
}
