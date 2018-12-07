package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Unbreaking extends CustomEnchantment{
	
	public Unbreaking() {
		setDefaultDisplayName("Unbreaking");
		setDefaultFiftyConstant(-10);
		setDefaultThirtyConstant(-3);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(8);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.UNCOMMON);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DURABILITY;
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
		if(ItemUtils.getItemTypes().get("trident").contains(item)){
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
		return "unbreaking";
	}

	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Increases effective durability." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Tools, Weapons, Armor, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: All." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
