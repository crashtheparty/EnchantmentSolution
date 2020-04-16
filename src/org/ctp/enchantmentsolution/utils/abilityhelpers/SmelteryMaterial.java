package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SmelteryMaterial {

	private ItemStack smelted;
	private Material fromMaterial, toMaterial;

	public SmelteryMaterial(ItemStack smelted, Material fromMaterial, Material toMaterial) {
		setSmelted(smelted);
		setFromMaterial(fromMaterial);
		setToMaterial(toMaterial);
	}

	public ItemStack getSmelted() {
		return smelted;
	}

	public void setSmelted(ItemStack smelted) {
		this.smelted = smelted;
	}

	public Material getFromMaterial() {
		return fromMaterial;
	}

	public void setFromMaterial(Material fromMaterial) {
		this.fromMaterial = fromMaterial;
	}

	public Material getToMaterial() {
		return toMaterial;
	}

	public void setToMaterial(Material toMaterial) {
		this.toMaterial = toMaterial;
	}
}
