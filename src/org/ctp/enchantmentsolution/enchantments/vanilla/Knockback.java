package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Knockback extends CustomEnchantment{
	
	public Knockback() {
		setDefaultDisplayName("Knockback");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(-15);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(20);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.UNCOMMON);
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
		if(ench.getName().equals("knockup")){
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "knockback";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.KNOCKBACK;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Increases knockback." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
