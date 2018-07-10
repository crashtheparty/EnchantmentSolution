package org.ctp.enchantmentsolution.enchantments.level30.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class KnockUp extends CustomEnchantment{
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.KNOCKUP;
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
		return canEnchantItem(item);
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equals(getName())){
			return true;
		}
		if(ench.getName().equals("knockback")){
			return true;
		}
		return false;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public String getName() {
		return "knockup";
	}

	@Override
	public String getDisplayName() {
		return "Knockup";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public int getWeight() {
		return 5;
	}
	
	public int[] enchantability(int level){
		int[] levels = new int[2];
		levels[0] = level * 20 - 15;
		levels[1] = levels[0] + 50;
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
		pageOne += "Description: Increases knockback upwards." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
