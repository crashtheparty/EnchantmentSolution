package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.items.nms.itemplace.ItemPlaceType_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.itemplace.ItemPlaceType_v1_14;

public interface ItemPlaceType {

	public List<Material> getItemPlaceTypes();
	
	public static List<Material> getPlaceTypes(){
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return new ItemPlaceType_v1_13().getItemPlaceTypes();
		case 4:
			return new ItemPlaceType_v1_14().getItemPlaceTypes();
		}
		return null;
	}
}
