package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.ItemDamageEvent;

public class TankEvent extends ItemDamageEvent {

	public TankEvent(Player who, ItemStack item, int oldDamage, int newDamage) {
		super(who, item, oldDamage, newDamage);
	}

}
