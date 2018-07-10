package org.ctp.enchantmentsolution.enchantments.wrappers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class MagmaWalkerWrapper extends CustomEnchantmentWrapper{

	public MagmaWalkerWrapper(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MAGMA_WALKER";
	}

	@Override
	public int getMaxLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStartLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean conflictsWith(Enchantment paramEnchantment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canEnchantItem(ItemStack paramItemStack) {
		// TODO Auto-generated method stub
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
