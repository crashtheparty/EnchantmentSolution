package org.ctp.enchantmentsolution.enchantments.wrappers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class ExpShareWrapper extends CustomEnchantmentWrapper{

	public ExpShareWrapper(int id) {
		super(id);
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
