package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Mending extends CustomEnchantment{
	
	public Mending() {
		setDefaultDisplayName("Mending");
		setTreasure(true);
		setDefaultFiftyConstant(25);
		setDefaultThirtyConstant(25);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(75);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
	}

	@Override
	public boolean canEnchantItem(Material item) {
		return false;
	}

	@Override
	public boolean canAnvilItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("all").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase("infinity")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "mending";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.MENDING;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Repair durability with experience." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: None." + StringUtils.LF;
		pageTwo += "Anvilable Items: All." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
