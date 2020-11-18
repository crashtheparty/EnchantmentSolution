package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class HeightWidthDepthEvent extends MultiBlockBreakEvent {

	private final EnchantmentLevel enchantmentWidth, enchantmentDepth;
	private final Block block;

	public HeightWidthDepthEvent(List<Location> blocks, Block block, Player player, int heightLevel, int widthLevel, int depthLevel) {
		super(blocks, player, new EnchantmentLevel(CERegister.HEIGHT_PLUS_PLUS, heightLevel));
		enchantmentWidth = new EnchantmentLevel(CERegister.WIDTH_PLUS_PLUS, widthLevel);
		enchantmentDepth = new EnchantmentLevel(CERegister.DEPTH_PLUS_PLUS, widthLevel);
		this.block = block;
	}

	public EnchantmentLevel getEnchantmentWidth() {
		return enchantmentWidth;
	}

	public EnchantmentLevel getEnchantmentDepth() {
		return enchantmentDepth;
	}

	public Block getBlock() {
		return block;
	}

}
