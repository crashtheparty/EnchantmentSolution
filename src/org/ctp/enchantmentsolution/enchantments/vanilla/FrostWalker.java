package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class FrostWalker extends CustomEnchantment{
	
	public FrostWalker() {
		setDefaultDisplayName("Frost Walker");
		setTreasure(true);
		setDefaultFiftyConstant(5);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(20);
		setDefaultThirtyMaxConstant(15);
		setDefaultFiftyStartLevel(10);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(2);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.RARE);
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
		if(ItemUtils.getItemTypes().get("boots").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("depth_strider")){
			return true;
		}
		if(ench.getName().equalsIgnoreCase("magma_walker")){
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "frost_walker";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.FROST_WALKER;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Creates frosted ice blocks when walking over water." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Boots, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Boots, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
