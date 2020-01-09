package org.ctp.enchantmentsolution.events.player;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class DropEvent extends ESPlayerEvent {

	private final List<ItemStack> originalDrops;
	private List<ItemStack> newDrops;
	private boolean override;

	public DropEvent(Player who, EnchantmentLevel enchantment, List<ItemStack> newDrops, List<ItemStack> originalDrops,
	boolean override) {
		super(who, enchantment);
		this.originalDrops = originalDrops;
		this.newDrops = newDrops;
		setOverride(override);
	}

	public List<ItemStack> getOriginalDrops() {
		return originalDrops;
	}

	public List<ItemStack> getNewDrops() {
		return newDrops;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}
}
