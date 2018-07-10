package org.ctp.enchantmentsolution.enchantments.level30.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Efficiency extends CustomEnchantment{
	
	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("tools").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean canAnvilItem(Material item) {
		if(ItemUtils.getItemTypes().get("tools").contains(item)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("shears").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		return false;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public String getName() {
		return "efficiency";
	}
	
	@Override
	public String getDisplayName() {
		return "Efficiency";
	}

	@Override
	public int getStartLevel() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getWeight() {
		return 10;
	}

	@Override
	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = 10 * level - 9;
		levels[1] = levels[0] + 50;
		return levels;
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DIG_SPEED;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Increases mining speed." + 
				StringUtils.LF + 
				"One must use the proper tool for a block in order to receive the speed. Does not matter if you mine it with the incorrect tier." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Tools, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Tools, Shears, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
