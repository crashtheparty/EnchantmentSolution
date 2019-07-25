package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public enum TransmutationLoot{
	RAW_SALMON(Material.SALMON, 1200, 1, 2), RAW_COD(Material.COD, 1200, 1, 2), TROPICAL_FISH(Material.TROPICAL_FISH, 750, 1, 1), 
	PUFFERFISH(Material.PUFFERFISH, 400, 1, 1), KELP(Material.KELP, 400, 1, 4), TRIDENT(Material.TRIDENT, 8, 1, 1), SCUTE(Material.SCUTE, 300, 1, 2), 
	TURTLE_EGG(Material.TURTLE_EGG, 50, 1, 1), INK_SAC(Material.INK_SAC, 1000, 1, 4), PRISMARINE_SHARD(Material.PRISMARINE_SHARD, 750, 1, 3), 
	PRISMARINE_CRYSTALS(Material.PRISMARINE_CRYSTALS, 500, 1, 3), NAUTILUS_SHELL(Material.NAUTILUS_SHELL, 140, 1, 1), 
	HEART_OF_THE_SEA(Material.HEART_OF_THE_SEA, 2, 1, 1), BRAIN_CORAL(Material.BRAIN_CORAL, 100, 1, 4), BUBBLE_CORAL(Material.BUBBLE_CORAL, 100, 1, 4),
	FIRE_CORAL(Material.FIRE_CORAL, 100, 1, 4), HORN_CORAL(Material.HORN_CORAL, 100, 1, 4), TUBE_CORAL(Material.TUBE_CORAL, 100, 1, 4), 
	BRAIN_CORAL_FAN(Material.BRAIN_CORAL_FAN, 100, 1, 4), BUBBLE_CORAL_FAN(Material.BUBBLE_CORAL_FAN, 100, 1, 4), FIRE_CORAL_FAN(Material.FIRE_CORAL_FAN, 100, 1, 4), 
	HORN_CORAL_FAN(Material.HORN_CORAL_FAN, 100, 1, 4), TUBE_CORAL_FAN(Material.TUBE_CORAL_FAN, 100, 1, 4), BRAIN_CORAL_BLOCK(Material.BRAIN_CORAL_BLOCK, 300, 1, 1), 
	BUBBLE_CORAL_BLOCK(Material.BUBBLE_CORAL_BLOCK, 300, 1, 1), FIRE_CORAL_BLOCK(Material.FIRE_CORAL_BLOCK, 300, 1, 1), 
	HORN_CORAL_BLOCK(Material.HORN_CORAL_BLOCK, 300, 1, 1), TUBE_CORAL_BLOCK(Material.TUBE_CORAL_BLOCK, 300, 1, 1), SPONGE(Material.SPONGE, 400, 1, 1), 
	SEA_PICKLE(Material.SEA_PICKLE, 400, 1, 4);
	
	private Material material;
	private int chance, min, max;
	
	TransmutationLoot(Material material, int chance, int min, int max) {
		setMaterial(material);
		setChance(chance);
		setMin(min);
		setMax(max);
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}
	
	public boolean canStack() {
		if(this == TransmutationLoot.TRIDENT) return false;
		return true;
	}
	
	public static ItemStack getLoot(Player player) {
		TransmutationLoot loot = getRandomLoot();
		ItemStack lootItem = new ItemStack(loot.getMaterial(), (int) ((Math.random() * (loot.getMax() - loot.getMin())) + loot.getMin()));
		if(!loot.canStack()) {
			int random = (int) (Math.random() * loot.getMaterial().getMaxDurability());
			lootItem = DamageUtils.setDamage(lootItem, random);
		}
		switch(lootItem.getType()) {
		case COD:
		case SALMON:
		case PUFFERFISH:
		case TROPICAL_FISH:
			AdvancementUtils.awardCriteria(player, ESAdvancement.FISHIER_BUSINESS, lootItem.getType().name().toLowerCase());
			break;
		case TRIDENT:
			AdvancementUtils.awardCriteria(player, ESAdvancement.POSEIDON_REBORN, "trident");
			break;
		default:
			break;
		}
		return lootItem;
	}
	
	public static boolean isTransmutatedLoot(ItemStack item) {
		for(TransmutationLoot loot : values()) {
			if(loot.getMaterial().equals(item.getType())) {
				return true;
			}
		}
		return false;
	}
	
	public static TransmutationLoot getRandomLoot() {
		int chance = 0;
		for(TransmutationLoot loot : values()) {
			chance += loot.getChance();
		}
		
		int random = (int) (Math.random() * chance);
		
		for(TransmutationLoot loot : values()) {
			random -= loot.getChance();
			if(random <= 0) {
				return loot;
			}
		}
		return null;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
