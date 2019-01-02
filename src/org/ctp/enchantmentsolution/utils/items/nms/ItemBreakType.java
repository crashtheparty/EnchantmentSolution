package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.nms.Version;
import org.ctp.enchantmentsolution.utils.items.nms.itembreak.ItemBreak_v1_13;

public interface ItemBreakType {
	public Material getMaterial();
	
	public List<Material> getBreakTypes();
	
	public static ItemBreakType getType(Material type) {
		if(Version.VERSION_NUMBER < 4) {
			return ItemBreak_v1_13.getType(type);
		} else if (Version.VERSION_NUMBER >= 4) {
			
		}
		return null;
	}
	
	public static List<Material> allBreakTypes(){
		if(Version.VERSION_NUMBER < 4) {
			return ItemBreak_v1_13.allBreakTypes();
		} else if (Version.VERSION_NUMBER >= 4) {
			
		}
		return null;
	}
}
