package org.ctp.enchantmentsolution.enchantments.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Life extends CustomEnchantment{
	
	public Life() {
		setDefaultDisplayName("Life");
		setDefaultFiftyConstant(-5);
		setDefaultThirtyConstant(-5);
		setDefaultFiftyModifier(25);
		setDefaultThirtyModifier(15);
		setDefaultFiftyMaxConstant(30);
		setDefaultThirtyMaxConstant(40);
		setDefaultFiftyStartLevel(10);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.LIFE;
	}

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("chestplates").contains(item)){
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
		return false;
	}
	
	@Override
	public String getName() {
		return "life";
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Increases maximum health by 2 hearts per level when worn." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Chestplates, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Chestplates, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
