package org.ctp.enchantmentsolution.utils.player;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;

public class EnchantmentDisable {
	private final JavaPlugin plugin;
	private final EnchantmentWrapper enchantment;

	public EnchantmentDisable(JavaPlugin plugin, EnchantmentWrapper enchantment) {
		this.plugin = plugin;
		this.enchantment = enchantment;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public EnchantmentWrapper getEnchantment() {
		return enchantment;
	}

	public boolean isSimilar(JavaPlugin plugin, EnchantmentWrapper enchantment) {
		return plugin.equals(this.plugin) && enchantment.equals(this.enchantment);
	}
}
