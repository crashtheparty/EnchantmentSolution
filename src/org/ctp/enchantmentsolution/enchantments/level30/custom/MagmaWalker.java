package org.ctp.enchantmentsolution.enchantments.level30.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class MagmaWalker extends CustomEnchantment{
	
	public MagmaWalker() {
		setTreasure(true);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.MAGMA_WALKER;
	}

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("boots").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean canAnvilItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("boots").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("frost_walker")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("depth_strider")){
			return true;
		}
		return false;
	}

	@Override
	public int getMaxLevel() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public String getName() {
		return "magma_walker";
	}

	@Override
	public String getDisplayName() {
		return "Magma Walker";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public int getWeight() {
		return 2;
	}

	@Override
	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = 10 * level;
		levels[1] = levels[0] + 15;
		return levels;
	}
	
	public int multiplier(Material material) {
		if(!(material.equals(Material.BOOK) || material.equals(Material.ENCHANTED_BOOK))) {
			return 4;
		}
		return 2;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Allows players to walk on lava, turning it into magma." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Boots, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Boots, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
