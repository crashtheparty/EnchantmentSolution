package org.ctp.enchantmentsolution.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class CustomEnchantmentWrapper extends EnchantmentWrapper {

	private final JavaPlugin plugin;

	CustomEnchantmentWrapper(String namespace, String name) {
		super(new NamespacedKey(EnchantmentSolution.getPlugin(), namespace), name);
		this.plugin = EnchantmentSolution.getPlugin();
	}

	public CustomEnchantmentWrapper(JavaPlugin plugin, String namespace, String name) {
		super(new NamespacedKey(plugin, namespace), name);
		this.plugin = plugin;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

}
