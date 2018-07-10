package org.ctp.enchantmentsolution.enchantments.level50.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Knockback extends CustomEnchantment{
	
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
		if(ench.getName().equals("knockup")){
			return true;
		}
		return false;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	public String getName() {
		return "knockback";
	}

	@Override
	public String getDisplayName() {
		return "Knockback";
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

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.KNOCKBACK;
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
		pageOne += "Description: Increases knockback." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
