package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.items.nms.itemrepair.ItemRepair_v1_13;

public interface ItemRepairType {
	public Material getMaterial();
	
	public List<Material> getRepairTypes();
	
	public static ItemRepairType getType(Material type) {
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() < 4) {
			return ItemRepair_v1_13.getType(type);
		} else if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() >= 4) {
			
		}
		return null;
	}
	
	public static ItemRepairType[] getValues(){
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() < 4) {
			return ItemRepair_v1_13.values();
		} else if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() >= 4) {
			
		}
		return null;
	}
}
