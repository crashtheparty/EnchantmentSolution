package org.ctp.enchantmentsolution.enchantments.level30.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class SoulReaper extends CustomEnchantment{
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SOUL_REAPER;
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
		if(ItemUtils.getItemTypes().get("hoes").contains(item)){
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
		return 1;
	}

	@Override
	public String getName() {
		return "soul_reaper";
	}

	@Override
	public String getDisplayName() {
		return "Soul Reaper";
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
		levels[0] = 20;
		levels[1] = 70;
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
		pageOne += "Description: Has a chance of stealing soulbounded items from the killed player." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Hoes, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
