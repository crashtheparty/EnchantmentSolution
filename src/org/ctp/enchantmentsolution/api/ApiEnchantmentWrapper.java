package org.ctp.enchantmentsolution.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;

public abstract class ApiEnchantmentWrapper extends CustomEnchantmentWrapper{

	private JavaPlugin plugin;
	
	public ApiEnchantmentWrapper(JavaPlugin plugin, String namespace) {
		super(plugin, namespace);
		
		this.plugin = plugin;
	}
	
	public JavaPlugin getPlugin() {
		return plugin;
	}

}
