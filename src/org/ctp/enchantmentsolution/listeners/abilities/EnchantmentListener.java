package org.ctp.enchantmentsolution.listeners.abilities;

import java.lang.reflect.Method;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;

public abstract class EnchantmentListener implements Listener{

	protected void runMethod(EnchantmentListener superClass, String name) {
		try {
			Method superMethod = superClass.getClass().getDeclaredMethod(name);
			superMethod.setAccessible(true);
			superMethod.invoke(superClass);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void runMethod(EnchantmentListener superClass, String name, Event event, Class<? extends Event> className) {
		try {
			Method superMethod = superClass.getClass().getDeclaredMethod(name, className);
			superMethod.setAccessible(true);
			superMethod.invoke(superClass, event);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected boolean canRun(Enchantment enchantment, Event event) {
		if(!DefaultEnchantments.isEnabled(enchantment)) return false;
		if(event instanceof Cancellable) {
			if(((Cancellable) event).isCancelled()) return false;
		}
		return true;
	}
	
	protected boolean canRun(Event event, boolean all, Enchantment...enchantments) {
		if(event instanceof Cancellable) {
			if(((Cancellable) event).isCancelled()) {
				return false;
			}
		}
		if(all) {
			for(Enchantment enchantment : enchantments) {
				if(!DefaultEnchantments.isEnabled(enchantment)) all = false;
			}
			if(!all) {
				return false;
			}
		} else {
			for(Enchantment enchantment : enchantments) {
				if(DefaultEnchantments.isEnabled(enchantment)) return true;
			}
			return false;
		}
		return true;
	}
}
