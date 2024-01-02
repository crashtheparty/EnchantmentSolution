package org.ctp.enchantmentsolution.interfaces;

import org.bukkit.event.EventPriority;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;

public interface ESInterface {

	public EnchantmentWrapper getEnchantment();

	public EventPriority getPriority();
}
