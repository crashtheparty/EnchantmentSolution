package org.ctp.enchantmentsolution.interfaces;

import java.util.HashMap;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.walker.MagmaWalker;
import org.ctp.enchantmentsolution.interfaces.walker.VoidWalker;

public class InterfaceRegistry {

	private static HashMap<EnchantmentWrapper, WalkerInterface> WALKER = new HashMap<EnchantmentWrapper, WalkerInterface>();

	public static void firstLoad() {
		registerWalkerEnchantment(new MagmaWalker());
		registerWalkerEnchantment(new VoidWalker());
	}

	public static boolean registerWalkerEnchantment(WalkerInterface inter) {
		if (WALKER.containsKey(inter.getEnchantment())) return false;
		WALKER.put(inter.getEnchantment(), inter);
		return true;
	}

	protected static HashMap<EnchantmentWrapper, WalkerInterface> getWalkerInterfaces() {
		return WALKER;
	}
}
