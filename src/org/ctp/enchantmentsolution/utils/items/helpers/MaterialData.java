package org.ctp.enchantmentsolution.utils.items.helpers;

import org.bukkit.Material;

public class MaterialData {

	private Material material;
	private String materialName;
	
	public MaterialData(String name) {
		this.materialName = name.toUpperCase();
		try {
			this.material = Material.valueOf(this.materialName);
		} catch (Exception ex) {
		}
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public String getMaterialName() {
		return materialName;
	}
}
