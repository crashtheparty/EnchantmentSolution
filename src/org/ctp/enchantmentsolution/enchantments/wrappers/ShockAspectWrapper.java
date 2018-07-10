package org.ctp.enchantmentsolution.enchantments.wrappers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class ShockAspectWrapper extends CustomEnchantmentWrapper{

	public ShockAspectWrapper(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public String getName() {
		return "SHOCK_ASPECT";
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
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTreasure() {
		// TODO Auto-generated method stub
		return false;
	}
}
