package org.ctp.enchantmentsolution.enums;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class ItemPlaceType {
	public static List<Material> getPlaceTypes() {
		List<Material> itemTypes = new ArrayList<Material>();
		for(String s: getPlaceStrings())
			try {
				itemTypes.add(Material.valueOf(s));
			} catch (Exception ex) {}
		return itemTypes;
	}

	private static List<String> getPlaceStrings() {
		List<String> itemTypes = new ArrayList<String>();
		itemTypes.addAll(ItemBreakType.DIAMOND_PICKAXE.getAllTypes());
		return itemTypes;
	}
}
