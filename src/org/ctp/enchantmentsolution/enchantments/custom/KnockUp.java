package org.ctp.enchantmentsolution.enchantments.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class KnockUp extends CustomEnchantment{
	
	public KnockUp() {
		setDefaultDisplayName("Knockup");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(40);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.UNCOMMON);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.KNOCKUP;
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
		return canEnchantItem(item);
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equals(getName())){
			return true;
		}
		if(ench.getName().equals("knockback")){
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "knockup";
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Increases knockback upwards." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
