package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class ZealEvent extends ProjectileSpawnEvent {

	private boolean takeFireCharge;
	private final boolean hasFireCharge;

	public ZealEvent(Player who, ItemStack item, boolean takeFireCharge, boolean hasFireCharge) {
		super(who, new EnchantmentLevel(CERegister.ZEAL, 1), item, 20);
		setTakeFireCharge(takeFireCharge);
		this.hasFireCharge = hasFireCharge;
	}

	public boolean takeFireCharge() {
		return takeFireCharge;
	}

	public void setTakeFireCharge(boolean takeFireCharge) {
		this.takeFireCharge = takeFireCharge;
	}

	public boolean hasFireCharge() {
		return hasFireCharge;
	}

	@Override
	public boolean willCancel() {
		return !hasFireCharge && takeFireCharge || super.willCancel();
	}

}
