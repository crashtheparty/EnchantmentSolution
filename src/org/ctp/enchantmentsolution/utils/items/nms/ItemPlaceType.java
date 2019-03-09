package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.items.nms.itemplace.ItemPlaceType_v1_13;

public interface ItemPlaceType {

	public List<Material> getItemPlaceTypes();
	
	public static List<Material> getPlaceTypes(){
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() < 4) {
			return new ItemPlaceType_v1_13().getItemPlaceTypes();
		} else if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() >= 4) {
			
		}
		return null;
	}
}
