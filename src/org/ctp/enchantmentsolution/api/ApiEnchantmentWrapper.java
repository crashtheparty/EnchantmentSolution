package org.ctp.enchantmentsolution.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;

public abstract class ApiEnchantmentWrapper extends CustomEnchantmentWrapper{

	private JavaPlugin plugin;
	/**
	 * Constructor for the ApiEnchantmentWrapper
	 * @param plugin - the plugin of the enchantment
	 * @param namespace - the standard name of the enchantment
	 */
	public ApiEnchantmentWrapper(JavaPlugin plugin, String namespace) {
		super(plugin, namespace);
		
		this.plugin = plugin;
	}
	
	/**
	 * Gets the plugin of this enchantment
	 * @return JavaPlugin - the plugin
	 */
	public JavaPlugin getPlugin() {
		return plugin;
	}

}
