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
		case 5:
		case 6:
			return ItemBreak_v1_14.getType(type);
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
		case 5:
		case 6:
			return ItemBreak_v1_14.allBreakTypes();
		}
		return null;
	}
}
