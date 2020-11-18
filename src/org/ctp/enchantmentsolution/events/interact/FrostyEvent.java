package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class FrostyEvent extends ProjectileSpawnEvent {

	private boolean takeSnowball;
	private final boolean hasSnowball;

	public FrostyEvent(Player who, ItemStack item, boolean takeSnowball, boolean hasSnowball) {
		super(who, new EnchantmentLevel(CERegister.FROSTY, 1), item, 2);
		setTakeSnowball(takeSnowball);
		this.hasSnowball = hasSnowball;
	}

	public boolean takeSnowball() {
		return takeSnowball;
	}

	public void setTakeSnowball(boolean takeSnowball) {
		this.takeSnowball = takeSnowball;
	}

	public boolean hasSnowball() {
		return hasSnowball;
	}

	@Override
	public boolean willCancel() {
		return !hasSnowball && takeSnowball || super.willCancel();
	}

}
