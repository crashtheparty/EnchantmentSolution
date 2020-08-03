package org.ctp.enchantmentsolution.utils.items;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.ItemBreakType;

public class FortuneUtils {
	private static List<Material> CROPS = Arrays.asList(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.NETHER_WART, Material.BEETROOTS, Material.COCOA_BEANS);

	public static ItemStack getFortuneForSmeltery(ItemStack smelted, ItemStack item, Material original) {
		if (EnchantmentUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			int level = EnchantmentUtils.getLevel(item, Enchantment.LOOT_BONUS_BLOCKS);
			switch (original.name()) {
				case "ANCIENT_DEBRIS":
					double extraAmount = Math.random() * (level * 0.15);
					double rand = Math.random();
					int amount = 1;
					while (extraAmount >= 1) {
						extraAmount--;
						amount++;
					}
					if (extraAmount > rand) amount++;
					if (amount > 1) smelted.setAmount(amount);
					break;
				case "IRON_ORE":
				case "GOLD_ORE":
				case "NETHER_GOLD_ORE":
					int multiply = (int) (Math.random() * (level + 2));
					if (multiply > 1) smelted.setAmount(smelted.getAmount() * multiply);
					break;
			}
		}
		return smelted;
	}

	public static Collection<ItemStack> getFortuneItems(ItemStack item, Block brokenBlock,
	Collection<ItemStack> priorItems) {
		int level = EnchantmentUtils.getLevel(item, Enchantment.LOOT_BONUS_BLOCKS);
		if (level <= 0) return priorItems;
		Iterator<ItemStack> iter = priorItems.iterator();
		List<ItemStack> duplicate = new ArrayList<ItemStack>();
		while (iter.hasNext())
			duplicate.add(iter.next());
		int min = 0;
		int max = 0;
		int actualMax = 0;
		if (brokenBlock.getBlockData() instanceof Ageable) {
			Ageable age = (Ageable) brokenBlock.getBlockData();
			if (CROPS.contains(brokenBlock.getType())) if (age.getAge() != age.getMaximumAge()) return priorItems;
		}

		double chance = 0;
		double random = 0;

		Material breakBlock = null;
		ItemBreakType itemBreak = null;
		boolean minMax = false;

		double saplingChance = 0;
		boolean setSaplingChance = false;
		double appleChance = 0;
		Material sapling = null;

		switch (brokenBlock.getType().name()) {
			case "DIAMOND_ORE":
			case "EMERALD_ORE":
			case "COAL_ORE":
			case "NETHER_QUARTZ_ORE":
			case "LAPIS_ORE":
				itemBreak = ItemBreakType.getType(item.getType());
				if (itemBreak != null && itemBreak.getBreakTypes().contains(brokenBlock.getType())) {
					ItemStack fortunableItem = duplicate.get(0);
					int multiply = (int) (Math.random() * level);
					if (multiply > 1) {
						fortunableItem.setAmount(1 * multiply);
						priorItems.clear();
						priorItems.add(fortunableItem);
					}
					for(int i = 0; i < duplicate.size(); i++) {
						boolean foundPrior = false;
						for(ItemStack prior: priorItems)
							if (prior.getType().equals(duplicate.get(i).getType())) foundPrior = true;
						if (!foundPrior) priorItems.add(duplicate.get(i));
					}
				}
				break;
			case "REDSTONE_ORE":
				minMax = true;
				itemBreak = ItemBreakType.getType(item.getType());
				if (itemBreak != null && itemBreak.getBreakTypes().contains(brokenBlock.getType())) {
					min = 4;
					max = 5 + level;
					breakBlock = Material.REDSTONE;
				}
			case "WEEPING_VINES":
			case "TWISTING_VINES":
				if (!minMax) {
					chance = 2.0d / 3.0d;
					random = Math.random();
					if (chance < random) {
						min = 1;
						max = level + 1;
					}
				}
				minMax = true;
			case "WHEAT":
				if (!minMax) {
					breakBlock = Material.WHEAT_SEEDS;
					min = 0;
					max = 3 + level;
				}
				minMax = true;
			case "BEETROOTS":
				if (!minMax) {
					breakBlock = Material.BEETROOT_SEEDS;
					min = 0;
					max = 3 + level;
				}
				minMax = true;
			case "CARROTS":
				if (!minMax) {
					breakBlock = Material.CARROT;
					min = 1;
					max = 4 + level;
				}
				minMax = true;
			case "POTATOES":
				if (!minMax) {
					breakBlock = Material.POTATO;
					min = 1;
					max = 4 + level;
				}
				minMax = true;
			case "NETHER_WART":
				if (!minMax) {
					breakBlock = Material.NETHER_WART;
					min = 2;
					max = 4 + level;
				}
				minMax = true;
			case "SEA_LANTERN":
				if (!minMax) {
					breakBlock = Material.PRISMARINE_CRYSTALS;
					min = 2;
					max = 3 + level;
					actualMax = 5;
				}
				minMax = true;
			case "MELON":
				if (!minMax) {
					breakBlock = Material.MELON_SLICE;
					min = 3;
					max = 7 + level;
					actualMax = 9;
				}
				minMax = true;
			case "GLOWSTONE":
				if (!minMax) {
					breakBlock = Material.GLOWSTONE_DUST;
					min = 2;
					max = 4 + level;
					actualMax = 4;
				}
				minMax = true;
			case "GRASS":
				if (!minMax) {
					for(ItemStack priorItem: priorItems)
						if (priorItem.getType().equals(Material.WHEAT_SEEDS)) {
							min = 1;
							max = 3 + level * 2;
							break;
						}
					breakBlock = Material.WHEAT_SEEDS;
				}

				int finalCount = (int) (Math.random() * (max - min)) + min;
				if (actualMax > 0 && finalCount > actualMax) finalCount = actualMax;
				ItemStack fortunableItem = new ItemStack(breakBlock, finalCount);

				fortunableItem.setAmount(finalCount);
				priorItems.clear();
				priorItems.add(fortunableItem);
				if (brokenBlock.getType().equals(Material.BEETROOTS)) priorItems.add(new ItemStack(Material.BEETROOT));
				else if (brokenBlock.getType().equals(Material.WHEAT)) priorItems.add(new ItemStack(Material.WHEAT));
				break;
			case "GRAVEL":
				chance = 0;
				switch (level) {
					case 1:
						chance = .14;
						break;
					case 2:
						chance = .25;
						break;
					default:
						chance = 1;
						break;
				}
				random = Math.random();
				if (chance > random) {
					priorItems.clear();
					priorItems.add(new ItemStack(Material.FLINT));
				}
				break;
			case "JUNGLE_LEAVES":
				if (sapling == null) {
					setSaplingChance = true;
					sapling = Material.JUNGLE_SAPLING;
					saplingChance = 1.0D / 10.0D;
					switch (level) {
						case 1:
							saplingChance = 1.0D / 36.0D;
							break;
						case 2:
							saplingChance = 1.0D / 32.0D;
							break;
						case 3:
							saplingChance = 1.0D / 24.0D;
							break;
						case 4:
							saplingChance = 1.0D / 16.0D;
							break;
					}
				}
			case "OAK_LEAVES":
				if (sapling == null) sapling = Material.OAK_SAPLING;
			case "DARK_OAK_LEAVES":
				if (sapling == null) sapling = Material.DARK_OAK_SAPLING;
				appleChance = 1.0D / 80.0D;
				switch (level) {
					case 1:
						appleChance = 1.0D / 180.0D;
						break;
					case 2:
						appleChance = 1.0D / 160.0D;
						break;
					case 3:
						appleChance = 1.0D / 120.0D;
						break;
					case 4:
						appleChance = 1.0D / 100.0D;
						break;
				}
			case "ACACIA_LEAVES":
			case "BIRCH_LEAVES":
			case "SPRUCE_LEAVES":
				if (sapling == null) sapling = Material.SPRUCE_SAPLING;
				if (!setSaplingChance) {
					saplingChance = 1.0D / 5.0D;
					switch (level) {
						case 1:
							saplingChance = 1.0D / 16.0D;
							break;
						case 2:
							saplingChance = 1.0D / 12.0D;
							break;
						case 3:
							saplingChance = 1.0D / 10.0D;
							break;
						case 4:
							saplingChance = 1.0D / 8.0D;
							break;
					}
				}
				random = Math.random();
				if (saplingChance > random) priorItems.add(new ItemStack(sapling, 1));
				random = Math.random();
				if (appleChance > random) priorItems.add(new ItemStack(Material.APPLE, 1));
		}

		return priorItems;
	}
}
