package org.ctp.enchantmentsolution.utils.player;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantmentDisable {
	private final JavaPlugin plugin;
	private final Enchantment enchantment;

	public EnchantmentDisable(JavaPlugin plugin, Enchantment enchantment) {
		this.plugin = plugin;
		this.enchantment = enchantment;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public boolean isSimilar(JavaPlugin plugin, Enchantment enchantment) {
		return plugin == this.plugin && enchantment == this.enchantment;
	}
}
