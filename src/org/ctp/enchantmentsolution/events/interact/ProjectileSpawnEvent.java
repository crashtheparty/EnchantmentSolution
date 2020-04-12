package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ProjectileSpawnEvent extends InteractEvent {

	public ProjectileSpawnEvent(Player who, EnchantmentLevel enchantment, ItemStack item) {
		super(who, enchantment, item);
	}
	
	public abstract boolean willCancel();

}
