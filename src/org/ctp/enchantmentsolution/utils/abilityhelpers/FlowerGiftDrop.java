package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;

public enum FlowerGiftDrop {
	DANDELION(.5), POPPY(.5), BLUE_ORCHID(.5), ALLIUM(.5), AZURE_BLUET(.5), RED_TULIP(.5), ORANGE_TULIP(.5), WHITE_TULIP(.5), PINK_TULIP(.5),
	OXEYE_DAISY(.5), LILAC(1), ROSE_BUSH(1), PEONY(1), SUNFLOWER(1), CORNFLOWER(.5), LILY_OF_THE_VALLEY(.5), WITHER_ROSE(0.01);

	private MatData material;
	private double chance;

	private FlowerGiftDrop(double chance) {
		material = new MatData(name());
		this.chance = chance;
	}

	public Material getMaterial() {
		return material.getMaterial();
	}

	public double getChance() {
		return chance;
	}

	public static boolean isDoubleFlower(Material material) {
		switch (material.name()) {
			case "SUNFLOWER":
			case "PEONY":
			case "ROSE_BUSH":
			case "LILAC":
				return true;
			default:
				return false;
		}
	}

	public static boolean isWitherRose(Material material) {
		return material == Material.WITHER_ROSE;
	}

	public static boolean isItem(Material material) {
		for(FlowerGiftDrop value: values())
			if (value.getMaterial() == material) return true;
		return false;
	}

	public static ItemStack getItem(Material material) {
		for(FlowerGiftDrop value: values())
			if (value.getMaterial() == material) {
				double random = Math.random();
				if (value.getChance() > random) return new ItemStack(material);
				return null;
			}
		return null;
	}
}
