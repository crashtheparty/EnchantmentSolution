package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class FireAspect extends CustomEnchantment{
	
	public FireAspect() {
		setDefaultDisplayName("Fire Aspect");
		setDefaultFiftyConstant(-25);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(30);
		setDefaultThirtyModifier(20);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(5);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.RARE);
	}
	
	public boolean canEnchantItem(Material item) {
		if(item.equals(Material.BOOK)){
			return true;
		}
		if(ItemUtils.getItemTypes().get("swords").contains(item)){
			return true;
		}
		return false;
	}

	public boolean canAnvilItem(Material item) {
		return canEnchantItem(item);
	}

	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equalsIgnoreCase(getName())){
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "fire_aspect";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.FIRE_ASPECT;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Sets the target on fire." + 
				StringUtils.LF + 
				"Fire Aspect adds 80 fireticks (4 seconds of burning) per level to the target." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Swords, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}
	
}
