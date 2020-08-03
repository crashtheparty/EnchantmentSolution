package org.ctp.enchantmentsolution.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.crashapi.item.ItemData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;

public class CustomEnchantmentWrapper extends Enchantment {

	private final String name;

	CustomEnchantmentWrapper(String namespace, String name) {
		super(new NamespacedKey(EnchantmentSolution.getPlugin(), namespace));
		this.name = name;
	}

	public CustomEnchantmentWrapper(JavaPlugin plugin, String namespace, String name) {
		super(new NamespacedKey(plugin, namespace));
		this.name = name;
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		CustomEnchantment ench = getCustomEnchantment();
		if (ench != null && ench.canEnchantItem(new ItemData(arg0))) return true;
		return false;
	}

	private CustomEnchantment getCustomEnchantment() {
		return RegisterEnchantments.getCustomEnchantment(this);
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		CustomEnchantment ench = getCustomEnchantment();
		CustomEnchantment conflict = RegisterEnchantments.getCustomEnchantment(arg0);
		if (ench != null && ench.conflictsWith(conflict)) return true;
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public int getMaxLevel() {
		return getCustomEnchantment() == null ? 0 : getCustomEnchantment().getMaxLevel();
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public boolean isCursed() {
		return getCustomEnchantment() == null ? false : getCustomEnchantment().isCurse();
	}

	@Override
	public boolean isTreasure() {
		return getCustomEnchantment() == null ? false : !getCustomEnchantment().getEnchantmentLocations().contains(EnchantmentLocation.NON_BOOK);
	}

	@Override
	public String getName() {
		return name;
	}

}
