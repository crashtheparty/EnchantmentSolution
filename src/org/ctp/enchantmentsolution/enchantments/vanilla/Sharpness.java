package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Sharpness extends CustomEnchantment{
	
	public Sharpness() {
		setDefaultDisplayName("Sharpness");
		setDefaultFiftyConstant(-12);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(13);
		setDefaultThirtyModifier(11);
		setDefaultFiftyMaxConstant(20);
		setDefaultThirtyMaxConstant(20);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.COMMON);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DAMAGE_ALL;
	}

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
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("swords").contains(item)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("axes").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("bane_of_arthropods")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("smite")){
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "sharpness";
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Increases melee damage." + 
				StringUtils.LF + 
				"Adds 1 (Half Heart) extra damage for the first level, and 0.5 (Heart Ã— 1â?„4) for each additional level." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Swords, Axes, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
