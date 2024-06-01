package org.ctp.enchantmentsolution.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.data.items.ItemData;

public class EnchantmentWrapper {
	private final NamespacedKey key;
	private final String name;

	protected EnchantmentWrapper(NamespacedKey key, String name) {
		this.key = key;
		this.name = name;
	}

	protected EnchantmentWrapper(String key, String name) {
		this.key = NamespacedKey.minecraft(key);
		this.name = name;
	}

	public boolean canEnchantItem(ItemStack arg0) {
		CustomEnchantment ench = getCustomEnchantment();
		if (ench != null && ench.canEnchantItem(new ItemData(arg0))) return true;
		return false;
	}

	public Enchantment getRelativeEnchantment() {
		return Registry.ENCHANTMENT.get(key);
	}

	private CustomEnchantment getCustomEnchantment() {
		return RegisterEnchantments.getCustomEnchantment(this);
	}

	public boolean conflictsWith(EnchantmentWrapper arg0) {
		CustomEnchantment ench = getCustomEnchantment();
		CustomEnchantment conflict = RegisterEnchantments.getCustomEnchantment(arg0);
		if (ench != null && ench.conflictsWith(conflict)) return true;
		return false;
	}

	public int getMaxLevel() {
		return getCustomEnchantment() == null ? 0 : getCustomEnchantment().getMaxLevel();
	}

	public int getStartLevel() {
		return 1;
	}

	public boolean isCursed() {
		return getCustomEnchantment() == null ? false : getCustomEnchantment().isCurse();
	}

	public String getName() {
		return name;
	}

	public NamespacedKey getKey() {
		return key;
	}

	@Override
	public boolean equals(Object object) {
		if (this.getKey() == null) return false;
		if (object instanceof EnchantmentWrapper) {
			EnchantmentWrapper wrapper = (EnchantmentWrapper) object;
			return this.getKey().equals(wrapper.getKey()) && this.getName().equals(wrapper.getName());
		}
		return false;
	}
}
