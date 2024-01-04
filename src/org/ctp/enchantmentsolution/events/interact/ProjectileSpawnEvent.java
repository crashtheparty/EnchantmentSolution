package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.Cooldownable;

public abstract class ProjectileSpawnEvent extends InteractEvent implements Cooldownable {

	private boolean cooldown = true;
	private int cooldownTicks;

	public ProjectileSpawnEvent(Player who, EnchantmentLevel enchantment, ItemStack item, int cooldownTicks) {
		super(who, enchantment, item);
		this.cooldownTicks = cooldownTicks;
	}

	@Override
	public boolean useCooldown() {
		return cooldown;
	}

	@Override
	public void setCooldown(boolean cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public int getCooldownTicks() {
		return cooldownTicks;
	}

	@Override
	public void setCooldownTicks(int ticks) {
		cooldownTicks = ticks;
	}

	public boolean willCancel() {
		return false;
	}

}
