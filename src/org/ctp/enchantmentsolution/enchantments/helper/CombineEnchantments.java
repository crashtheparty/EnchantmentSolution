package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.List;

public class CombineEnchantments {

	private int cost;
	private List<EnchantmentLevel> enchantments;
	
	public CombineEnchantments(int cost, List<EnchantmentLevel> enchantments) {
		this.setCost(cost);
		this.setEnchantments(enchantments);
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public List<EnchantmentLevel> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<EnchantmentLevel> enchantments) {
		this.enchantments = enchantments;
	}
}
