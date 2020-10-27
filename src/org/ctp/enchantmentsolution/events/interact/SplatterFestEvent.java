package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class SplatterFestEvent extends ProjectileSpawnEvent {

	private boolean takeEgg;
	private final boolean hasEgg;

	public SplatterFestEvent(Player who, ItemStack item, boolean takeEgg, boolean hasEgg) {
		super(who, new EnchantmentLevel(CERegister.SPLATTER_FEST, 1), item, 2);
		setTakeEgg(takeEgg);
		this.hasEgg = hasEgg;
	}

	public boolean takeEgg() {
		return takeEgg;
	}

	public void setTakeEgg(boolean takeEgg) {
		this.takeEgg = takeEgg;
	}

	public boolean hasEgg() {
		return hasEgg;
	}

	@Override
	public boolean willCancel() {
		return !hasEgg && takeEgg || super.willCancel();
	}

}
