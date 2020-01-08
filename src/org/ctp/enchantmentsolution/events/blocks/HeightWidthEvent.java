package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class HeightWidthEvent extends MultiBlockBreakEvent {

	private final EnchantmentLevel enchantmentWidth;

	public HeightWidthEvent(Collection<Location> blocks, Player player, int heightLevel, int widthLevel) {
		super(blocks, player, new EnchantmentLevel(CERegister.HEIGHT_PLUS_PLUS, heightLevel));
		enchantmentWidth = new EnchantmentLevel(CERegister.WIDTH_PLUS_PLUS, widthLevel);
	}

	public EnchantmentLevel getEnchantmentWidth() {
		return enchantmentWidth;
	}

}
