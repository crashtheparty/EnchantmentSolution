package org.ctp.enchantmentsolution.events.player;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class DropEvent extends ESPlayerEvent implements Cancellable {

	private final List<ItemStack> originalDrops;
	private List<ItemStack> newDrops;
	private boolean override, cancelled;

	public DropEvent(Player who, EnchantmentLevel enchantment, List<ItemStack> newDrops, List<ItemStack> originalDrops,
	boolean override) {
		super(who, enchantment);
		this.originalDrops = originalDrops;
		setNewDrops(newDrops);
		setOverride(override);
	}

	public List<ItemStack> getOriginalDrops() {
		return originalDrops;
	}

	public List<ItemStack> getNewDrops() {
		return newDrops;
	}

	public void setNewDrops(List<ItemStack> newDrops) {
		this.newDrops = newDrops;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
