package org.ctp.enchantmentsolution.enchantments.level30.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Telepathy extends CustomEnchantment{
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.TELEPATHY;
	}

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
		return 1;
	}

	@Override
	public String getName() {
		return "telepathy";
	}

	@Override
	public String getDisplayName() {
		return "Telepathy";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public int getWeight() {
		return 1;
	}

	@Override
	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = 35;
		levels[1] = 85;
		return levels;
	}
	
	public int multiplier(Material material) {
		if(!(material.equals(Material.BOOK) || material.equals(Material.ENCHANTED_BOOK))) {
			return 8;
		}
		return 4;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Items mined go straight into your inventory." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Tools, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Tools, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
