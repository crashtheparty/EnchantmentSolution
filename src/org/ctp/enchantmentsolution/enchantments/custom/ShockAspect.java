package org.ctp.enchantmentsolution.enchantments.custom;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class ShockAspect extends CustomEnchantment{
	
	public ShockAspect() {
		setDefaultDisplayName("Shock Aspect");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(20);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(5);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.RARE);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SHOCK_ASPECT;
	}

	@Override
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("axes").contains(item)){
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
		return false;
	}
	
	@Override
	public String getName() {
		return "shock_aspect";
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Has a chance to strike lightning on attacked mobs." + StringUtils.LF;
		String pageTwo = "Enabled: " + isEnabled() + ". " + StringUtils.LF;
		pageTwo += "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Axes, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Axes, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}
}
