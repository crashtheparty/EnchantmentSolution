package org.ctp.enchantmentsolution.enchantments.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Smite extends CustomEnchantment{
	
	public Smite() {
		setDefaultDisplayName("Smite");
		setDefaultFiftyConstant(-4);
		setDefaultThirtyConstant(-3);
		setDefaultFiftyModifier(9);
		setDefaultThirtyModifier(8);
		setDefaultFiftyMaxConstant(18);
		setDefaultThirtyMaxConstant(20);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.UNCOMMON);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DAMAGE_UNDEAD;
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
		if(ench.getName().equalsIgnoreCase("sharpness")){
			return true;
		}
		return false;
	}
	
	@Override
	public String getName() {
		return "smite";
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Increases damage to \"undead\" mobs (skeletons, zombies, withers, wither skeletons, zombie pigmen, skeleton horses and zombie horses)" + 
				StringUtils.LF + 
				"Each level separately adds 2.5 (hearts Ã— 1 1â?„4) extra damage to each hit, to \"undead\" mobs only." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: Helmets, Books." + StringUtils.LF;
		pageTwo += "Anvilable Items: Helmets, Books." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
