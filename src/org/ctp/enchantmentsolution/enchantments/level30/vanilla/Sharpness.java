package org.ctp.enchantmentsolution.enchantments.level30.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Sharpness extends CustomEnchantment{
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DAMAGE_ALL;
	}

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("swords").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean canAnvilItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("swords").contains(item)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("axes").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("bane_of_arthropods")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("smite")){
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
		return "sharpness";
	}

	@Override
	public String getDisplayName() {
		return "Sharpness";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public int getWeight() {
		return 10;
	}

	@Override
	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = level * 11 - 10;
		levels[1] = levels[0] + 20;
		return levels;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Increases melee damage." + 
				StringUtils.LF + 
				"Adds 1 (Half Heart) extra damage for the first level, and 0.5 (Heart Ã— 1â?„4) for each additional level." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Swords, Axes, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
