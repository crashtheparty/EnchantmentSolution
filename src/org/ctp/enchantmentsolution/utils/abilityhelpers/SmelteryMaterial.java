package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public class SmelteryMaterial {

	private ItemStack smelted;
	private Material fromMaterial, toMaterial;
	private final BlockData data;

	public SmelteryMaterial(ItemStack smelted, Material fromMaterial, Material toMaterial, BlockData data) {
		setSmelted(smelted);
		setFromMaterial(fromMaterial);
		setToMaterial(toMaterial);
		this.data = data;
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

	public BlockData getData() {
		return data;
	}
}
