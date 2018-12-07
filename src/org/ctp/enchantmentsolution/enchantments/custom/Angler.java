package org.ctp.enchantmentsolution.enchantments.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Angler extends CustomEnchantment{
	
	public Angler() {
		setDefaultDisplayName("Angler");
		setDefaultFiftyConstant(0);
		setDefaultThirtyConstant(0);
		setDefaultFiftyModifier(10);
		setDefaultThirtyModifier(8);
		setDefaultFiftyMaxConstant(30);
		setDefaultThirtyMaxConstant(25);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.ANGLER;
	}

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("fishing").contains(item)){
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
		if(ench.getName().equals(getName())) {
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "angler";
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Catch one additional fish per level if you caught a fish." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Fishing Rod, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Fishing Rod, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
