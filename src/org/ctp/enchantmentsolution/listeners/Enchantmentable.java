package org.ctp.enchantmentsolution.listeners;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.Reflectionable;

public abstract class Enchantmentable implements Listener, Reflectionable {

	public boolean canRun(EnchantmentWrapper enchantment, Event event) {
		if (!RegisterEnchantments.isEnabled(enchantment)) return false;
		if (event instanceof Cancellable && ((Cancellable) event).isCancelled()) return false;
		return true;
	}

	public boolean canRun(Event event, boolean all, EnchantmentWrapper... enchantments) {
		if (event instanceof Cancellable && ((Cancellable) event).isCancelled()) return false;
		if (all) {
			for(EnchantmentWrapper enchantment: enchantments)
				if (!RegisterEnchantments.isEnabled(enchantment)) all = false;
			return all;
		} else {
			for(EnchantmentWrapper enchantment: enchantments)
				if (RegisterEnchantments.isEnabled(enchantment)) return true;
			return false;
		}
	}
}
