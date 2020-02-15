package org.ctp.enchantmentsolution.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class CustomEnchantmentWrapper extends Enchantment {

	private final String name;
	private final int maxLevel;

	CustomEnchantmentWrapper(String namespace, String name, int maxLevel) {
		super(new NamespacedKey(EnchantmentSolution.getPlugin(), namespace));
		this.name = name;
		this.maxLevel = maxLevel;
	}

	public CustomEnchantmentWrapper(JavaPlugin plugin, String namespace, String name) {
		this(plugin, namespace, name, 0);
	}

	public CustomEnchantmentWrapper(JavaPlugin plugin, String namespace, String name, int maxLevel) {
		super(new NamespacedKey(plugin, namespace));
		this.name = name;
		this.maxLevel = maxLevel;
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

}
