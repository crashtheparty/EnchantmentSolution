package org.ctp.enchantmentsolution.enchantments.wrappers;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public abstract class CustomEnchantmentWrapper extends Enchantment{

	public CustomEnchantmentWrapper(String namespace) {
		super(new NamespacedKey(EnchantmentSolution.getPlugin(), namespace));
	}
	
	public CustomEnchantmentWrapper(JavaPlugin plugin, String namespace) {
		super(new NamespacedKey(plugin, namespace));
	}

}
