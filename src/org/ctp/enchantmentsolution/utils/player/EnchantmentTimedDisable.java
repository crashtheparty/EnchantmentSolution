package org.ctp.enchantmentsolution.utils.player;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;

public class EnchantmentTimedDisable {

	private final JavaPlugin plugin;
	private final EnchantmentWrapper enchantment;
	private long endTime;

	public EnchantmentTimedDisable(JavaPlugin plugin, EnchantmentWrapper enchantment, long endTime) {
		this.plugin = plugin;
		this.enchantment = enchantment;
		setEndTime(endTime);
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public EnchantmentWrapper getEnchantment() {
		return enchantment;
	}

	public long getEndTime() {
		return endTime;
	}

	public void addToEndTime(long add) {
		endTime += add;
	}

	public void removeFromEndTime(long remove) {
		endTime -= remove;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public boolean isSimilar(JavaPlugin plugin, EnchantmentWrapper enchantment) {
		return plugin.equals(this.plugin) && enchantment.equals(this.enchantment);
	}

}
