package org.ctp.enchantmentsolution.utils.items.nms;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.items.nms.moisturize.Moisturize_v1_13;

public interface ItemMoisturizeType {
	public String getName();
	
	public static ItemMoisturizeType getType(Material type) {
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() < 4) {
			return Moisturize_v1_13.getMoisturizeType(type);
		} else if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() >= 4) {
			
		}
		return null;
	}
	
	public static Material getUnsmelt(Material type){
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() < 4) {
			return Moisturize_v1_13.getUnsmelt(type);
		} else if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() >= 4) {
			
		}
		return null;
	}
	
	public static Material getWet(Material type){
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() < 4) {
			return Moisturize_v1_13.getWet(type);
		} else if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() >= 4) {
			
		}
		return null;
	}
}
