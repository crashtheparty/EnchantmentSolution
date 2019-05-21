package org.ctp.enchantmentsolution.utils.items.nms;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.items.nms.moisturize.Moisturize_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.moisturize.Moisturize_v1_14;

public interface ItemMoisturizeType {
	public String getName();
	
	public static ItemMoisturizeType getType(Material type) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return Moisturize_v1_13.getMoisturizeType(type);
		case 4:
		case 5:
			return Moisturize_v1_14.getMoisturizeType(type);
		}
		return null;
	}
	
	public static Material getUnsmelt(Material type){
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return Moisturize_v1_13.getUnsmelt(type);
		case 4:
		case 5:
			return Moisturize_v1_14.getUnsmelt(type);
		}
		return null;
	}
	
	public static Material getWet(Material type){
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return Moisturize_v1_13.getWet(type);
		case 4:
		case 5:
			return Moisturize_v1_14.getWet(type);
		}
		return null;
	}
}
