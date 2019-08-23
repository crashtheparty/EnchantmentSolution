package org.ctp.enchantmentsolution.utils.items.helpers;

import org.bukkit.Material;

public class FortuneMaterial {	
	private String materialName, breakMaterialName;
	private Material material, breakMaterial;
	private int min, max, level, actualMax;
	
	public FortuneMaterial(String materialName, int min, int max){
		this.materialName = materialName;
		try {
			this.material = Material.valueOf(materialName);
		} catch (Exception ex) {
			
		}
		this.breakMaterialName = this.materialName;
		this.breakMaterial = this.material;
		this.min = min;
		this.max = max + level;
		this.actualMax = 0;
	}
	
	public FortuneMaterial(String materialName, int min, int max, int actualMax){
		this.materialName = materialName;
		try {
			this.material = Material.valueOf(materialName);
		} catch (Exception ex) {
			
		}
		this.breakMaterialName = this.materialName;
		this.breakMaterial = this.material;
		this.min = min;
		this.max = max;
		this.actualMax = actualMax;
	}
	
	public FortuneMaterial(String materialName, String breakMaterialName, int min, int max){
		this.materialName = materialName;
		try {
			this.material = Material.valueOf(materialName);
		} catch (Exception ex) {
			
		}
		this.breakMaterialName = breakMaterialName;
		try {
			this.breakMaterial = Material.valueOf(breakMaterialName);
		} catch (Exception ex) {
			
		}
		this.min = min;
		this.max = max;
		this.actualMax = 0;
	}
	
	public FortuneMaterial(String materialName, String breakMaterialName, int min, int max, int actualMax){
		this.materialName = materialName;
		try {
			this.material = Material.valueOf(materialName);
		} catch (Exception ex) {
			
		}
		this.breakMaterialName = breakMaterialName;
		try {
			this.breakMaterial = Material.valueOf(breakMaterialName);
		} catch (Exception ex) {
			
		}
		this.min = min;
		this.max = max;
		this.actualMax = actualMax;
	}

	public String getMaterialName() {
		return materialName;
	}

	public String getBreakMaterialName() {
		return breakMaterialName;
	}

	public Material getMaterial() {
		return material;
	}

	public Material getBreakMaterial() {
		return breakMaterial;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getLevel() {
		return level;
	}

	public int getActualMax() {
		return actualMax;
	}
}
