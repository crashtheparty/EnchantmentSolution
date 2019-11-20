package org.ctp.enchantmentsolution.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;

public interface Reflectionable {

	default void runMethod(Reflectionable superClass, String name) {
		try {
			Method superMethod = superClass.getClass().getDeclaredMethod(name);
			superMethod.setAccessible(true);
			superMethod.invoke(superClass);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	default void runMethod(Reflectionable superClass, String name, Event event, Class<? extends Event> className) {
		try {
			Method superMethod = superClass.getClass().getDeclaredMethod(name, className);
			superMethod.setAccessible(true);
			superMethod.invoke(superClass, event);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	default void runMethod(Reflectionable superClass, String name, Event event, Enchantment enchantment) {
		try {
			List<Class<?>> clazzes = new ArrayList<Class<?>>();
			clazzes.add(event.getClass());
			clazzes.add(Enchantment.class);

			Method superMethod = superClass.getClass().getDeclaredMethod(name, clazzes.toArray(new Class<?>[] {}));
			superMethod.setAccessible(true);
			superMethod.invoke(superClass, event, enchantment);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	default boolean canRun(Enchantment enchantment) {
		return RegisterEnchantments.isEnabled(enchantment);
	}

	default boolean canRun(boolean all, Enchantment... enchantments) {
		if (all) {
			for(Enchantment enchantment: enchantments) {
				if (!RegisterEnchantments.isEnabled(enchantment)) {
					all = false;
				}
			}
			return all;
		} else {
			for(Enchantment enchantment: enchantments) {
				if (RegisterEnchantments.isEnabled(enchantment)) {
					return true;
				}
			}
			return false;
		}
	}
}
