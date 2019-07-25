package org.ctp.enchantmentsolution.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class CustomEnchantmentWrapper extends Enchantment{

	private String name;
	
	public CustomEnchantmentWrapper(String namespace, String name) {
		super(new NamespacedKey(EnchantmentSolution.getPlugin(), namespace));
		this.name = name;
	}
	
	public CustomEnchantmentWrapper(JavaPlugin plugin, String namespace, String name) {
		super(new NamespacedKey(plugin, namespace));
		this.name = name;
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
		return 0;
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	public boolean isCursed() {
		return false;
	}

	public boolean isTreasure() {
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

}
