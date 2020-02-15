package org.ctp.enchantmentsolution.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;

public class ApiEnchantmentWrapper extends CustomEnchantmentWrapper {

	private final JavaPlugin plugin;

	/**
	 * Constructor for the ApiEnchantmentWrapper
	 * 
	 * @param plugin
	 *            - the plugin of the enchantment
	 * @param namespace
	 *            - the standard name of the enchantment
	 * @param name
	 *            - the standard name of the enchantment
	 */
	public ApiEnchantmentWrapper(JavaPlugin plugin, String namespace, String name) {
		this(plugin, namespace, name, 0);
	}

	/**
	 * Constructor for the ApiEnchantmentWrapper
	 * 
	 * @param plugin
	 *            - the plugin of the enchantment
	 * @param namespace
	 *            - the standard name of the enchantment
	 * @param name
	 *            - the standard name of the enchantment
	 * @param maxLevel
	 *            - the standard maximum level of the enchantment
	 */
	public ApiEnchantmentWrapper(JavaPlugin plugin, String namespace, String name, int maxLevel) {
		super(plugin, namespace, name, maxLevel);

		if (name == null || name == "") throw new NullPointerException("An enchantment's name may not be set to null or an empty string!");
		this.plugin = plugin;
	}

	/**
	 * Gets the plugin of this enchantment
	 * 
	 * @return JavaPlugin - the plugin
	 */
	public JavaPlugin getPlugin() {
		return plugin;
	}
}
