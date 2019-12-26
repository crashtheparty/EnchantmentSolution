package org.ctp.enchantmentsolution.events.player;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class BonusDropsEvent extends ESPlayerEvent {

	private static final HandlerList handlers = new HandlerList();

	public final static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public final HandlerList getHandlers() {
		return handlers;
	}

	private Collection<ItemStack> drops;
	private int multiplyAmount;

	public BonusDropsEvent(Player who, EnchantmentLevel enchantment, Collection<ItemStack> drops, int multiplyAmount) {
		super(who, enchantment);
		setDrops(drops);
		setMultiplyAmount(multiplyAmount);
	}

	public Collection<ItemStack> getDrops() {
		return drops;
	}

	public void setDrops(Collection<ItemStack> drops) {
		this.drops = drops;
	}

	public int getMultiplyAmount() {
		return multiplyAmount;
	}

	public void setMultiplyAmount(int multiplyAmount) {
		this.multiplyAmount = multiplyAmount;
	}

}
