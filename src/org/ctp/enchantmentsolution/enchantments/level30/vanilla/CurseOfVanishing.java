package org.ctp.enchantmentsolution.enchantments.level30.vanilla;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class CurseOfVanishing extends CustomEnchantment{
	
	public CurseOfVanishing() {
		setTreasure(true);
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.VANISHING_CURSE;
	}

	@Override
	public boolean canEnchantItem(Material item) {
		return false;
	}

	@Override
	public boolean canAnvilItem(Material item) {
		if(ItemUtils.getItemTypes().get("all").contains(item)){
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(CustomEnchantment ench) {
		if(ench.getName().equals(getName())) {
			return true;
		}
		return false;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "vanishing_curse";
	}

	@Override
	public String getDisplayName() {
		return "Curse of Vanishing";
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public int getWeight() {
		return 1;
	}

	@Override
	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = 25;
		levels[1] = 50;
		return levels;
	}
	
	@Override
	public String[] getPage() {
		String pageOne = "Name: " + getDisplayName() + StringUtils.LF + StringUtils.LF;
		pageOne += "Description: Causes the item to disappear on death." + 
				StringUtils.LF + 
				"When the player dies, the item disappears instead of dropping on the ground. The item may still be dropped normally." + StringUtils.LF;
		String pageTwo = "Max Level: " + getMaxLevel() + "."+ StringUtils.LF;
		pageTwo += "Weight: " + getWeight() + "."+ StringUtils.LF;
		pageTwo += "Start Level: " + getStartLevel() + "."+ StringUtils.LF;
		pageTwo += "Enchantable Items: None." + StringUtils.LF;
		pageTwo += "Anvilable Items: All." + StringUtils.LF;
		pageTwo += "Treasure Enchantment: " + isTreasure() + ". " + StringUtils.LF;
		return new String[] {pageOne, pageTwo};
	}

}
