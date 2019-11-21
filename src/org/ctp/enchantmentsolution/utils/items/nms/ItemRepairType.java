package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.items.nms.itemrepair.ItemRepair_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.itemrepair.ItemRepair_v1_14;

public interface ItemRepairType {
	public Material getMaterial();
	
	public List<Material> getRepairTypes();
	
	public static ItemRepairType getType(Material type) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return ItemRepair_v1_13.getType(type);
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return ItemRepair_v1_14.getType(type);
		}
		return null;
	}
	
	public static ItemRepairType[] getValues(){
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return ItemRepair_v1_13.values();
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return ItemRepair_v1_14.values();
		}
		return null;
	}
}
