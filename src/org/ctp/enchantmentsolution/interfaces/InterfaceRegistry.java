package org.ctp.enchantmentsolution.interfaces;

import java.util.HashMap;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.interfaces.walker.MagmaWalker;
import org.ctp.enchantmentsolution.interfaces.walker.VoidWalker;

public class InterfaceRegistry {

	private static HashMap<Enchantment, WalkerInterface> WALKER = new HashMap<Enchantment, WalkerInterface>();

	public static void firstLoad() {
		registerWalkerEnchantment(new MagmaWalker());
		registerWalkerEnchantment(new VoidWalker());
	}

	public static boolean registerWalkerEnchantment(WalkerInterface inter) {
		if (WALKER.containsKey(inter.getEnchantment())) return false;
		WALKER.put(inter.getEnchantment(), inter);
		return true;
	}

	protected static HashMap<Enchantment, WalkerInterface> getWalkerInterfaces() {
		return WALKER;
	}
}
