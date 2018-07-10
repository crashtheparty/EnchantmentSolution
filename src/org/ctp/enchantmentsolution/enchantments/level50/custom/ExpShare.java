package org.ctp.enchantmentsolution.enchantments.level50.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class ExpShare extends CustomEnchantment{
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.EXP_SHARE;
	}

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("swords").contains(item)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("tools").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean canAnvilItem(Material item) {
		return canEnchantItem(item);
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
		return 3;
	}

	@Override
	public String getName() {
		return "exp_share";
	}

	@Override
	public String getDisplayName() {
		return "Exp. Share";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public int getWeight() {
		return 5;
	}

	@Override
	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = level * 20;
		levels[1] = levels[0] + 40;
		return levels;
	}
	
	public int multiplier(Material material) {
		if(!(material.equals(Material.BOOK) || material.equals(Material.ENCHANTED_BOOK))) {
			return 2;
		}
		return 1;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Increase experience earned from killing mobs and breaking blocks." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Swords, Tools, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Swords, Tools, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
