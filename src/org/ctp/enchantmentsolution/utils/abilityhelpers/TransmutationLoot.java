package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.Locale;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public enum TransmutationLoot {
	SALMON(1200, 1, 2), COD(1200, 1, 2), TROPICAL_FISH(750, 1, 1), PUFFERFISH(400, 1, 1), KELP(400, 1, 4), TRIDENT(8, 1, 1), SCUTE(300, 1, 2),
	TURTLE_EGG(50, 1, 1), INK_SAC(1000, 1, 4), PRISMARINE_SHARD(750, 1, 3), PRISMARINE_CRYSTALS(500, 1, 3), NAUTILUS_SHELL(140, 1, 1),
	HEART_OF_THE_SEA(2, 1, 1), BRAIN_CORAL(100, 1, 4), BUBBLE_CORAL(100, 1, 4), FIRE_CORAL(100, 1, 4), HORN_CORAL(100, 1, 4), TUBE_CORAL(100, 1, 4),
	BRAIN_CORAL_FAN(100, 1, 4), BUBBLE_CORAL_FAN(100, 1, 4), FIRE_CORAL_FAN(100, 1, 4), HORN_CORAL_FAN(100, 1, 4), TUBE_CORAL_FAN(100, 1, 4),
	BRAIN_CORAL_BLOCK(300, 1, 1), BUBBLE_CORAL_BLOCK(300, 1, 1), FIRE_CORAL_BLOCK(300, 1, 1), HORN_CORAL_BLOCK(300, 1, 1), TUBE_CORAL_BLOCK(300, 1, 1),
	SPONGE(400, 1, 1), SEA_PICKLE(400, 1, 4);

	private MatData material;
	private int chance, min, max;

	TransmutationLoot(int chance, int min, int max) {
		setMaterial(new MatData(name()));
		setChance(chance);
		setMin(min);
		setMax(max);
	}

	public Material getMaterial() {
		return material.getMaterial();
	}

	public void setMaterial(MatData material) {
		this.material = material;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	public boolean canStack() {
		if (this == TransmutationLoot.TRIDENT) return false;
		return true;
	}

	public static ItemStack getLoot(Player player) {
		TransmutationLoot loot = getRandomLoot();
		ItemStack lootItem = new ItemStack(loot.getMaterial(), (int) (Math.random() * (loot.getMax() - loot.getMin() + 1) + loot.getMin()));
		if (!loot.canStack()) {
			int random = (int) (Math.random() * DamageUtils.getMaxDamage(lootItem));
			lootItem = DamageUtils.setDamage(lootItem, random);
		}
		switch (lootItem.getType().name()) {
			case "COD":
			case "SALMON":
			case "PUFFERFISH":
			case "TROPICAL_FISH":
				AdvancementUtils.awardCriteria(player, ESAdvancement.FISHY_BUSINESS, lootItem.getType().name().toLowerCase(Locale.ROOT));
				break;
			case "TRIDENT":
				AdvancementUtils.awardCriteria(player, ESAdvancement.POSEIDON_REBORN, "trident");
				break;
			default:
				break;
		}
		return lootItem;
	}

	public static boolean isTransmutatedLoot(ItemStack item) {
		for(TransmutationLoot loot: values())
			if (loot.getMaterial().equals(item.getType())) return true;
		return false;
	}

	public static TransmutationLoot getRandomLoot() {
		int chance = 0;
		for(TransmutationLoot loot: values())
			if (loot.getMaterial() != null) chance += loot.getChance();

		int random = (int) (Math.random() * chance);

		for(TransmutationLoot loot: values()) {
			random -= loot.getChance();
			if (random <= 0) return loot;
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
