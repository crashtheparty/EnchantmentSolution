package org.ctp.enchantmentsolution.enums;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.utils.files.ItemBreakFile;

public class ItemPlaceType {
	public static List<Material> getPlaceTypes() {
		return ItemBreakFile.getAllMaterials();
	}
}
