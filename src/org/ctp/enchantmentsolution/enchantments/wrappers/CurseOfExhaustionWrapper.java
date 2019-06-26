package org.ctp.enchantmentsolution.enchantments.wrappers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class CurseOfExhaustionWrapper extends CustomEnchantmentWrapper{

	public CurseOfExhaustionWrapper() {
		super("exhaustion_curse");
	}

	@Override
	public String getName() {
		return "EXHAUSTION_CURSE";
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

	@Override
	public boolean isCursed() {
		return true;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

}
