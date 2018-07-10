package org.ctp.enchantmentsolution.enchantments.wrappers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class FrequentFlyerWrapper extends CustomEnchantmentWrapper{

	public FrequentFlyerWrapper(int id) {
		super(id);
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "FREQUENT_FLYER";
	}

	@Override
	public int getStartLevel() {
		// TODO Auto-generated method stub
		return 0;
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
