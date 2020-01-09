package org.ctp.enchantmentsolution.enums;

import org.bukkit.Material;

public class MatData {

	private Material material;
	private String materialName;

	public MatData(String name) {
		materialName = name.toUpperCase();
		try {
			material = Material.valueOf(materialName);
		} catch (Exception ex) {}
	}

	public Material getMaterial() {
		return material;
	}

	public String getMaterialName() {
		return materialName;
	}

	public boolean equals(Object obj) {
		if (obj instanceof MatData) {
			MatData data = (MatData) obj;
			return data.material != null && data.material == material;
		}
		return false;
	}
}
