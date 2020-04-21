package org.ctp.enchantmentsolution.events.drops;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class DropEvent extends ESPlayerEvent {

	private List<ItemStack> drops;

	public DropEvent(Player who, EnchantmentLevel enchantment, List<ItemStack> drops) {
		super(who, enchantment);
		this.drops = drops;
	}
	public List<ItemStack> getDrops() {
		return drops;
	}
}
