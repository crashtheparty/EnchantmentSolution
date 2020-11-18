package org.ctp.enchantmentsolution.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.Reflectionable;

public abstract class Enchantmentable implements Listener, Reflectionable {
	
	protected boolean canRun(Enchantment enchantment, Event event) {
		if (!RegisterEnchantments.isEnabled(enchantment)) return false;
		if (event instanceof Cancellable && ((Cancellable) event).isCancelled()) return false;
		return true;
	}

	protected boolean canRun(Event event, boolean all, Enchantment... enchantments) {
		if (event instanceof Cancellable && ((Cancellable) event).isCancelled()) return false;
		if (all) {
			for(Enchantment enchantment: enchantments)
				if (!RegisterEnchantments.isEnabled(enchantment)) all = false;
			return all;
		} else {
			for(Enchantment enchantment: enchantments)
				if (RegisterEnchantments.isEnabled(enchantment)) return true;
			return false;
		}
	}
}
