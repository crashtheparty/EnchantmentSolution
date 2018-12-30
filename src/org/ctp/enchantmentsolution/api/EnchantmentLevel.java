package org.ctp.enchantmentsolution.api;

import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;

public class EnchantmentLevel {

	private CustomEnchantment enchant;
	private int level;
	
	public EnchantmentLevel(CustomEnchantment enchant, int level){
		this.enchant = enchant;
		this.level = level;
	}
	
	public CustomEnchantment getEnchant(){
		return enchant;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
}
