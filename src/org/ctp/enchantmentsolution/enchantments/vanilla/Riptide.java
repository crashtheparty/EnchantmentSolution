package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Riptide extends CustomEnchantment{
	
	public Riptide() {
		setDefaultDisplayName("Riptide");
		setDefaultFiftyConstant(7);
		setDefaultThirtyConstant(9);
		setDefaultFiftyModifier(11);
		setDefaultThirtyModifier(9);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(15);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
	}

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("trident").contains(item)){
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
		if(ench.getName().equalsIgnoreCase("channeling")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("loyalty")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "riptide";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.RIPTIDE;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Returns a thrown trident after it hits something." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Helmets, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Helmets, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}
}
