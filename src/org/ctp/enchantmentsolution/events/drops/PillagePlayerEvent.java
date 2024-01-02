package org.ctp.enchantmentsolution.events.drops;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class PillagePlayerEvent extends ESPlayerEvent {

	private Collection<ItemStack> drops;
	private boolean override;

	public PillagePlayerEvent(Player who, int level, Collection<ItemStack> drops, boolean override) {
		super(who, new EnchantmentLevel(CERegister.PILLAGE, level));
		this.drops = drops;
		this.override = override;
	}

	public Collection<ItemStack> getDrops() {
		return drops;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

}
