package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class BlastProtection extends CustomEnchantment{
	
	public BlastProtection() {
		setDefaultDisplayName("Blast Protection");
		setDefaultFiftyConstant(-8);
		setDefaultThirtyConstant(-3);
		setDefaultFiftyModifier(14);
		setDefaultThirtyModifier(8);
		setDefaultFiftyMaxConstant(19);
		setDefaultThirtyMaxConstant(12);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(4);
		setDefaultWeight(Weight.RARE);
	}
	
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
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("armor").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase("fire_protection")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("protection")){
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
	public String getName() {
		return "blast_protection";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.PROTECTION_EXPLOSIONS;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Reduces explosion damage." + 
				StringUtils.LF + 
				"Also reduces explosion knockback by (15 × level)%. If multiple pieces have the enchantment, only the highest level's reduction is used." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Armor, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Armor, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
