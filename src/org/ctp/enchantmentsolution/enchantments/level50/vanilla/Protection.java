package org.ctp.enchantmentsolution.enchantments.level50.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Protection extends CustomEnchantment{
	
	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("armor").contains(item)){
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
		if(ench.getName().equalsIgnoreCase("fire_protection")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("blast_protection")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("projectile_protection")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase(getName())){
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
		return "protection";
	}
	
	@Override
	public String getDisplayName() {
		return "Protection";
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
		levels[0] = 16 * level - 15;
		levels[1] = levels[0] + 35;
		return levels;
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.PROTECTION_ENVIRONMENTAL;
	}

	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Reduces all damage, except damage from the Void, the /kill command, or hunger damage." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Armor, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Armor, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
