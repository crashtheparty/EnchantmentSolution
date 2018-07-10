package org.ctp.enchantmentsolution.enchantments;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public abstract class CustomEnchantment{
	
	private boolean enabled = true;
	private boolean treasure = false;
	
	public Enchantment getRelativeEnchantment() {
		return null;
	}

	public boolean canEnchantItem(Material item) {
		return false;
	}
	
	public boolean canAnvilItem(Material item) {
		return false;
	}

	public boolean conflictsWith(CustomEnchantment ench) {
		return false;
	}

	public int getMaxLevel() {
		return 0;
	}

	public String getName() {
		return null;
	}
	
	public String getDisplayName() {
		return null;
	}

	public int getStartLevel() {
		return 0;
	}
	
	public int getWeight() {
		return 0;
	}
	
	public boolean canEnchant(int enchantability, int level){
		if(level < getStartLevel()){
			return false;
		}
		int enchantabilityHigh = enchantability(getMaxLevel())[1];
		int enchantabilityLow = enchantability(1)[0];
		if(enchantability >= enchantabilityLow && enchantability <= enchantabilityHigh){
			return true;
		}
		
		return false;
	}
	
	public int getEnchantLevel(int enchantability){
		for(int i = getMaxLevel(); i > 0; i--){
			int[] levels = enchantability(i);
			if(enchantability >= levels[0] && enchantability <= levels[1]){
				return i;
			}
		}
		return 0;
	}
	
	public int[] enchantability(int level) {
		return null;
	}
	
	public int multiplier(Material material) {
		return 1;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isTreasure() {
		return treasure;
	}
	
	public void setTreasure(boolean treasure) {
		this.treasure = treasure;
	}
	
	public String[] getPage() {
		return null;
	}

}
