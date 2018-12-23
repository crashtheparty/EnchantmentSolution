package org.ctp.enchantmentsolution.enchantments.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Soulbound extends CustomEnchantment{
	
	public Soulbound() {
		setDefaultDisplayName("Soulbound");
		setDefaultFiftyConstant(40);
		setDefaultThirtyConstant(30);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(30);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SOULBOUND;
	}

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("tools").contains(item)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("swords").contains(item)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("armor").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean canAnvilItem(Material item) {
		if(ItemUtils.getItemTypes().get("all").contains(item)){
			return true;
		}
		return canEnchantItem(item);
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		return false;
	}
	
	@Override
	public String getName() {
		return "soulbound";
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Keep item on death." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Tools, Swords, Armor, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: All." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
