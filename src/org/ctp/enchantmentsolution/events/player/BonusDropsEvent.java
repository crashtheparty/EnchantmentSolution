package org.ctp.enchantmentsolution.events.player;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class BonusDropsEvent extends ESPlayerEvent {

	private Collection<ItemStack> drops;
	private int multiplyAmount;

	public BonusDropsEvent(Player who, EnchantmentLevel enchantment, Collection<ItemStack> drops, int multiplyAmount) {
		super(who, enchantment);
		this.drops = drops;
		setMultiplyAmount(multiplyAmount);
	}

	public Collection<ItemStack> getDrops() {
		return drops;
	}

	public int getMultiplyAmount() {
		return multiplyAmount;
	}

	public void setMultiplyAmount(int multiplyAmount) {
		this.multiplyAmount = multiplyAmount;
	}

}
