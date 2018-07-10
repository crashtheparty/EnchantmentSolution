package org.ctp.enchantmentsolution.enchantments.level50.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class DepthStrider extends CustomEnchantment{
	
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
		return canEnchantItem(item);
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("frost_walker")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("magma_walker")){
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
		return "depth_strider";
	}
	
	@Override
	public String getDisplayName() {
		return "Depth Strider";
	}

	@Override
	public int getStartLevel() {
		return 10;
	}

	@Override
	public int getWeight() {
		return 2;
	}

	@Override
	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = 15 * level + 5;
		levels[1] = levels[0] + 25;
		return levels;
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DEPTH_STRIDER;
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
		pageOne += "Description: Increases underwater movement speed." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Boots, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Boots, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
