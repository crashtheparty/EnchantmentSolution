package org.ctp.enchantmentsolution.enchantments.level30.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class AquaAffinity extends CustomEnchantment{

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("helmets").contains(item)){
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
		return "aqua_affinity";
	}
	
	@Override
	public String getDisplayName() {
		return "Aqua Affinity";
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
		levels[0] = 1;
		levels[1] = levels[0] + 40;
		return levels;
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.WATER_WORKER;
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
		pageOne += "Description: Increases underwater mining rate." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Helmets, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Helmets, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}
}
