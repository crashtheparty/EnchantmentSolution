package org.ctp.enchantmentsolution.enchantments.level50.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class FireAspect extends CustomEnchantment{
	
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("swords").contains(item)){
			return true;
		}
		return false;
	}

	public boolean canAnvilItem(Material item) {
		return canEnchantItem(item);
	}

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
		return "fire_aspect";
	}
	
	@Override
	public String getDisplayName() {
		return "Fire Aspect";
	}

	@Override
	public int getStartLevel() {
		return 5;
	}

	@Override
	public int getWeight() {
		return 2;
	}

	@Override
	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = 30 * level - 25;
		levels[1] = levels[0] + 50;
		return levels;
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.FIRE_ASPECT;
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
		pageOne += "Description: Sets the target on fire." + 
				StringUtils.LF + 
				"Fire Aspect adds 80 fireticks (4 seconds of burning) per level to the target." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}
	
}
