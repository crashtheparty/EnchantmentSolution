package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.ItemDamageEvent;

public class InstabilityEvent extends ItemDamageEvent {

	public InstabilityEvent(Player who, ItemStack item, int oldDamage, int newDamage) {
		super(who, new EnchantmentLevel(CERegister.CURSE_OF_INSTABILITY, 1), item, oldDamage, newDamage);
	}

}
