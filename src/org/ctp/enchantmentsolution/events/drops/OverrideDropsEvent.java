package org.ctp.enchantmentsolution.events.drops;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class OverrideDropsEvent extends DropEvent {

	private final List<ItemStack> originalDrops;
	private boolean override;

	public OverrideDropsEvent(Player who, EnchantmentLevel enchantment, List<ItemStack> drops, List<ItemStack> originalDrops, boolean override) {
		super(who, enchantment, drops);
		this.originalDrops = originalDrops;
		setOverride(override);
	}

	public List<ItemStack> getOriginalDrops() {
		return originalDrops;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

}
