package org.ctp.enchantmentsolution.enchantments.level50.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class FrequentFlyer extends CustomEnchantment{
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.FREQUENT_FLYER;
	}

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		return false;
	}

	@Override
	public boolean canAnvilItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("elytra").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equals(getName())) {
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
		return "frequent_flyer";
	}

	@Override
	public String getDisplayName() {
		return "Frequent Flyer";
	}

	@Override
	public int getStartLevel() {
		// TODO Auto-generated method stub
		return 30;
	}

	@Override
	public int getWeight() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = 15 * level + 20;
		levels[1] = levels[0] + 40;
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
		pageOne += "Description: Allows flight. Durability damage every (3 * level) seconds when below 255 height and every (level) seconds above 255 height. Removes flight at 32 durability." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Elytra, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
