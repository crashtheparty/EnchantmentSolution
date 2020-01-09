package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.ItemDamageEvent;

public class TankEvent extends ItemDamageEvent {

	public TankEvent(Player who, int level, ItemStack item, int oldDamage, int newDamage) {
		super(who, new EnchantmentLevel(CERegister.TANK, level), item, oldDamage, newDamage);
	}

}
