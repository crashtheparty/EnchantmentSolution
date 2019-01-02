package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.nms.Version;
import org.ctp.enchantmentsolution.utils.items.nms.itemrepair.ItemRepair_v1_13;

public interface ItemRepairType {
	public Material getMaterial();
	
	public List<Material> getRepairTypes();
	
	public static ItemRepairType getType(Material type) {
		if(Version.VERSION_NUMBER < 4) {
			return ItemRepair_v1_13.getType(type);
		} else if (Version.VERSION_NUMBER >= 4) {
			
		}
		return null;
	}
	
	public static ItemRepairType[] getValues(){
		if(Version.VERSION_NUMBER < 4) {
			return ItemRepair_v1_13.values();
		} else if (Version.VERSION_NUMBER >= 4) {
			
		}
		return null;
	}
}
