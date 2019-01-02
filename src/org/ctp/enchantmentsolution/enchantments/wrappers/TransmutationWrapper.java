package org.ctp.enchantmentsolution.enchantments.wrappers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class TransmutationWrapper extends CustomEnchantmentWrapper{

	public TransmutationWrapper() {
		super("transmutation");
	}

	@Override
	public String getName() {
		return "TRANSMUTATION";
	}

	@Override
	public int getMaxLevel() {
		return 0;
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public boolean conflictsWith(Enchantment paramEnchantment) {
		return false;
	}

	@Override
	public boolean canEnchantItem(ItemStack paramItemStack) {
		return false;
	}

	public boolean isCursed() {
		return false;
	}

	public boolean isTreasure() {
		return false;
	}

}
