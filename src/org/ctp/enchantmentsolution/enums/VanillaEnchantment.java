package org.ctp.enchantmentsolution.enums;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

public enum VanillaEnchantment {

	MULTISHOT("multishot"), PIERCING("piercing"), QUICK_CHARGE("quick_charge"), SOUL_SPEED("soul_speed");

	private final NamespacedKey key;
	private final Enchantment enchantment;

	VanillaEnchantment(String nameKey) {
		key = NamespacedKey.minecraft(nameKey);
		Enchantment ench = null;
		try {
			ench = Enchantment.getByKey(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		enchantment = ench;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}
}
