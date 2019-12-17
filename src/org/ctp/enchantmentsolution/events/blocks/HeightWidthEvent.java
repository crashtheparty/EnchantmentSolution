package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class HeightWidthEvent extends MultiBlockBreakEvent {

	private final EnchantmentLevel enchantmentWidth;
	
	public HeightWidthEvent(Collection<Block> blocks, Player player, int heightLevel, int widthLevel) {
		super(blocks, player, new EnchantmentLevel(CERegister.HEIGHT_PLUS_PLUS, heightLevel));
		this.enchantmentWidth = new EnchantmentLevel(CERegister.WIDTH_PLUS_PLUS, widthLevel);
	}

	public EnchantmentLevel getEnchantmentWidth() {
		return enchantmentWidth;
	}

}
