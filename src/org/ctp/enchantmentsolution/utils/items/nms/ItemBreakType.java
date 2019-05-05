package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.items.nms.itembreak.ItemBreak_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.itembreak.ItemBreak_v1_14;

public interface ItemBreakType {
	public Material getMaterial();
	
	public List<Material> getBreakTypes();
	
	public static ItemBreakType getType(Material type) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return ItemBreak_v1_13.getType(type);
		case 4:
			return ItemBreak_v1_14.getType(type);
		}
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() < 4) {
			return ItemBreak_v1_13.getType(type);
		} else if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() >= 4) {
			
		}
		return null;
	}
	
	public static List<Material> allBreakTypes(){
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return ItemBreak_v1_13.allBreakTypes();
		case 4:
			return ItemBreak_v1_14.allBreakTypes();
		}
		return null;
	}
}
