package org.ctp.enchantmentsolution.enchantments.wrappers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class ExpShareWrapper extends CustomEnchantmentWrapper{

	public ExpShareWrapper() {
		super("exp_share");
	}

	@Override
	public String getName() {
		return "EXP_SHARE";
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
