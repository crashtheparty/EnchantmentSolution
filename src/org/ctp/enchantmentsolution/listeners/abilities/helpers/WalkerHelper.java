package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.metadata.FixedMetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;

public class WalkerHelper {

	private Enchantment enchantment;
	private List<Material> types = new ArrayList<Material>();
	private Material replace;
	private String metadata;
	private final FixedMetadataValue value = new FixedMetadataValue(EnchantmentSolution.getPlugin(), new Integer(4));
	
	public WalkerHelper(Enchantment enchantment) {
		this.enchantment = enchantment;
		if(enchantment == DefaultEnchantments.MAGMA_WALKER) {
			replace = Material.MAGMA_BLOCK;
			types.add(Material.LAVA);
			metadata = "MagmaWalker";
		} else if(enchantment == DefaultEnchantments.VOID_WALKER) {
			replace = Material.OBSIDIAN;
			types.addAll(Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR));
			metadata = "VoidWalker";
		}
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public List<Material> getTypes() {
		return types;
	}

	public String getMetadata() {
		return metadata;
	}

	public FixedMetadataValue getValue() {
		return value;
	}

	public Material getReplace() {
		return replace;
	}
	
	public int getStage(int data) {
		switch(data) {
		case 4:
			return 10;
		case 3:
			return 2;
		case 2:
			return 4;
		case 1:
			return 6;
		case 0:
			return 8;
		default:
			return 10;
		}
	}
	
}
