package org.ctp.enchantmentsolution.nms.flowergift;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum FlowerGiftChance_v1_13 implements FlowerGiftChance{
	DANDELION(Material.DANDELION, .5), POPPY(Material.POPPY, .5), BLUE_ORCHID(Material.BLUE_ORCHID, .5), ALLIUM(Material.ALLIUM, .5),
	AZURE_BLUET(Material.AZURE_BLUET, .5), RED_TULIP(Material.RED_TULIP, .5), ORANGE_TULIP(Material.ORANGE_TULIP, .5), WHITE_TULIP(Material.WHITE_TULIP, .5),
	PINK_TULIP(Material.PINK_TULIP, .5), OXEYE_DAISY(Material.OXEYE_DAISY, .5), LILAC(Material.LILAC, 1), ROSE_BUSH(Material.ROSE_BUSH, 1),
	PEONY(Material.PEONY, 1), SUNFLOWER(Material.SUNFLOWER, 1);
	
	private Material material;
	private double chance;
	
	private FlowerGiftChance_v1_13(Material material, double chance) {
		this.material = material;
		this.chance = chance;
	}

	public Material getMaterial() {
		return material;
	}

	public double getChance() {
		return chance;
	}
	
	public static boolean isDoubleFlower(Material material) {
		switch(material) {
		case SUNFLOWER:
		case PEONY:
		case ROSE_BUSH:
		case LILAC:
			return true;
		default: 
			return false;
		}
	}

	public static boolean isWitherRose(Material material2) {
		return false;
	}
	
	public static boolean isItem(Material material) {
		for(FlowerGiftChance value : values()) {
			if(value.getMaterial() == material) {
				return true;
			}
		}
		return false;
	}
	
	public static ItemStack getItem(Material material) {
		for(FlowerGiftChance value : values()) {
			if(value.getMaterial() == material) {
				double random = Math.random();
				if(value.getChance() > random) {
					return new ItemStack(material);
				}
				return null;
			}
		}
		return null;
	}
}
