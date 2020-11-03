package org.ctp.enchantmentsolution.utils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

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

	default void runMethod(Reflectionable superClass, String name, Event event, Enchantment enchantment,
	PotionEffectType type) {
		try {
			List<Class<?>> clazzes = new ArrayList<Class<?>>();
			clazzes.add(event.getClass());
			clazzes.add(Enchantment.class);
			clazzes.add(PotionEffectType.class);

			Method superMethod = superClass.getClass().getDeclaredMethod(name, clazzes.toArray(new Class<?>[] {}));
			superMethod.setAccessible(true);
			superMethod.invoke(superClass, event, enchantment, type);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	default void runMethod(Reflectionable superClass, String name, Event event, Enchantment enchantment,
	Object... objs) {
		try {
			List<Class<?>> clazzes = new ArrayList<Class<?>>();
			clazzes.add(event.getClass());
			clazzes.add(Enchantment.class);
			for(Object obj: objs)
				clazzes.add(obj.getClass());

			Method superMethod = superClass.getClass().getDeclaredMethod(name, clazzes.toArray(new Class<?>[] {}));
			superMethod.setAccessible(true);
			superMethod.invoke(superClass, event, enchantment, objs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	default void runMethod(Reflectionable superClass, String name, LinkedHashMap<Class<?>, Object> map) {
		try {
			List<Class<?>> clazzes = new ArrayList<Class<?>>();
			List<Object> objs = new ArrayList<Object>();
			Iterator<Entry<Class<?>, Object>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Class<?>, Object> entry = iterator.next();
				if (entry.getKey().isAssignableFrom(entry.getValue().getClass())) {
					clazzes.add(entry.getKey());
					objs.add(entry.getValue());
				}
			}
			Method superMethod = superClass.getClass().getDeclaredMethod(name, clazzes.toArray(new Class<?>[] {}));
			superMethod.setAccessible(true);
			superMethod.invoke(superClass, objs.toArray());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	default boolean canRun(Enchantment enchantment) {
		return RegisterEnchantments.isEnabled(enchantment);
	}

	default boolean canRun(boolean all, Enchantment... enchantments) {
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

	default boolean isDisabled(Player player, Enchantment enchantment) {
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (esPlayer == null) return false;
		return esPlayer.hasTimedDisable(player, enchantment) || esPlayer.hasDisabled(player, enchantment);
	}
}
