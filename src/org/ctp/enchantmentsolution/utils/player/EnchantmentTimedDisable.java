package org.ctp.enchantmentsolution.utils.player;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantmentTimedDisable {

	private final JavaPlugin plugin;
	private final Enchantment enchantment;
	private long endTime;

	public EnchantmentTimedDisable(JavaPlugin plugin, Enchantment enchantment, long endTime) {
		this.plugin = plugin;
		this.enchantment = enchantment;
		setEndTime(endTime);
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public Enchantment getEnchantment() {
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

	public boolean isSimilar(JavaPlugin plugin, Enchantment enchantment) {
		return plugin == this.plugin && enchantment == this.enchantment;
	}

}
