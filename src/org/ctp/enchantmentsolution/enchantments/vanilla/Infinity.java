package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Infinity extends CustomEnchantment{
	
	public Infinity() {
		setDefaultDisplayName("Infinity");
		setDefaultFiftyConstant(35);
		setDefaultThirtyConstant(20);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(30);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
	}
	
	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("bow").contains(item)){
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
		if(ench.getName().equalsIgnoreCase("mending")){
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "infinity";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.ARROW_INFINITE;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Shooting doesn't consume regular arrows." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Bows, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Bows, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
