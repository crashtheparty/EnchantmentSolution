package org.ctp.enchantmentsolution.events.fishing;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class FishingEvent extends ESPlayerEvent {

	private final ItemStack originalItem;
	private ItemStack drop;
	private boolean override = true;
	private int exp;

	public FishingEvent(Player who, EnchantmentLevel enchantment, ItemStack drop, ItemStack originalItem, boolean override, int exp) {
		super(who, enchantment);
		this.drop = drop;
		this.originalItem = originalItem;
		this.override = override;
		this.exp = exp;
	}

	public ItemStack getOriginalItem() {
		return originalItem;
	}

	public boolean willOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public ItemStack getDrop() {
		return drop;
	}

}
