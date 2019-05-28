package org.ctp.enchantmentsolution.utils.items.nms.fortune;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;

public class Fortune_v1_13 {
	private static List<Material> FORTUNE_ITEMS = Arrays.asList(
			Material.DIAMOND_ORE, Material.EMERALD_ORE,
			Material.COAL_ORE, Material.NETHER_QUARTZ_ORE, Material.LAPIS_ORE,
			Material.REDSTONE_ORE, Material.REDSTONE_ORE, Material.WHEAT, Material.CARROTS,
			Material.POTATOES, Material.GLOWSTONE, Material.SEA_LANTERN,
			Material.MELON, Material.NETHER_WART, Material.BEETROOTS, 
			Material.GRASS, Material.GRAVEL, Material.JUNGLE_LEAVES, Material.OAK_LEAVES,
			Material.DARK_OAK_LEAVES, Material.ACACIA_LEAVES, Material.BIRCH_LEAVES, Material.SPRUCE_LEAVES);
	private static List<Material> CROPS = Arrays.asList(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.NETHER_WART, 
			Material.BEETROOTS, Material.COCOA_BEANS);

	public static Collection<ItemStack> getFortuneItems(ItemStack item,
			Block brokenBlock, Collection<ItemStack> priorItems) {
		boolean ironGold = false;
		int level = Enchantments.getLevel(item,
				Enchantment.LOOT_BONUS_BLOCKS);
		if(level <= 0) return priorItems;
		Iterator<ItemStack> iter = priorItems.iterator();
		List<ItemStack> duplicate = new ArrayList<ItemStack>();
		while(iter.hasNext()) {
			duplicate.add(iter.next());
		}
		if ((brokenBlock.getType().equals(Material.IRON_ORE) || brokenBlock
				.getType().equals(Material.GOLD_ORE))
				&& item.containsEnchantment(DefaultEnchantments.SMELTERY)) {
			if(DefaultEnchantments.isEnabled(DefaultEnchantments.SMELTERY)) {
				priorItems.clear();
				if (brokenBlock.getType().equals(Material.IRON_ORE)) {
					if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE).contains(item.getType())) {
						priorItems.add(new ItemStack(Material.IRON_INGOT));
						ironGold = true;
					}
				} else {
					if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE).contains(item.getType())){
						priorItems.add(new ItemStack(Material.GOLD_INGOT));
						ironGold = true;
					}
				}
				iter = priorItems.iterator();
				duplicate = new ArrayList<ItemStack>();
				while(iter.hasNext()) {
					duplicate.add(iter.next());
				}
			}
			
		} else if (!(FORTUNE_ITEMS.contains(brokenBlock.getType()))) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
				if(DefaultEnchantments.isEnabled(DefaultEnchantments.SMELTERY)) {
					ItemStack smelted = AbilityUtils.getSmelteryItem(brokenBlock, item);
					if(smelted != null) {
						priorItems.clear();
						priorItems.add(smelted);
					}
				}
			}
			return priorItems;
		}

		if (Arrays.asList(Material.DIAMOND_ORE, Material.EMERALD_ORE,
				Material.COAL_ORE, Material.NETHER_QUARTZ_ORE, Material.LAPIS_ORE)
				.contains(brokenBlock.getType())
				|| ironGold) {
			boolean canBreak = false;
			switch(brokenBlock.getType()) {
			case COAL_ORE:
			case NETHER_QUARTZ_ORE:
				if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
					canBreak = true;
				}
				break;
			case LAPIS_ORE:
				if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE).contains(item.getType())) {
					canBreak = true;
				}
				break;
			case EMERALD_ORE:
			case DIAMOND_ORE:
				if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE).contains(item.getType())) {
					canBreak = true;
				}
				break;
			default:
				break;
			}
			if(canBreak || ironGold) {
				ItemStack fortunableItem = duplicate.get(0);
				level = Enchantments.getLevel(item,
						Enchantment.LOOT_BONUS_BLOCKS) + 2;
				int multiply = (int) (Math.random() * level);
				if (multiply > 1) {
					fortunableItem.setAmount(fortunableItem.getAmount() * multiply);
					priorItems.clear();
					priorItems.add(fortunableItem);
				}
				for (int i = 0; i < duplicate.size(); i++) {
					boolean foundPrior = false;
					for (ItemStack prior : priorItems) {
						if (prior.getType().equals(duplicate.get(i).getType())) {
							foundPrior = true;
						}
					}
					if (!foundPrior) {
						priorItems.add(duplicate.get(i));
					}
				}
			}
		} else if (Arrays.asList(Material.REDSTONE_ORE, Material.WHEAT, Material.COCOA_BEANS,
				Material.BEETROOTS, Material.CARROTS, Material.POTATOES,
				Material.NETHER_WART, Material.SEA_LANTERN, Material.MELON, Material.GLOWSTONE, Material.GRASS).contains(
				brokenBlock.getType())) {
			level = Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS);
			int min = 0;
			int max = 0;
			int actualMax = 0;
			if(brokenBlock.getBlockData() instanceof Ageable) {
				Ageable age = (Ageable) brokenBlock.getBlockData();
				if(CROPS.contains(brokenBlock.getType())) {
					if(age.getAge() != age.getMaximumAge()) {
						return priorItems;
					}
				}
			}
			Material breakBlock = null;
			switch(brokenBlock.getType()){
			case REDSTONE_ORE:
				if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE).contains(item.getType())) {
					min = 4;
					max = 5 + level;
					breakBlock = Material.REDSTONE;
				}
				break;
			case GRASS:
				for(ItemStack priorItem : priorItems) {
					if(priorItem.getType().equals(Material.WHEAT_SEEDS)){
						min = 1;
						max = 3 + level * 2;
						break;
					}
				}
				breakBlock = Material.WHEAT_SEEDS;
				break;
			case WHEAT:
				breakBlock = Material.WHEAT_SEEDS;
				min = 0;
				max = 3 + level;
				break;
			case BEETROOTS:
				breakBlock = Material.BEETROOT_SEEDS;
				min = 0;
				max = 3 + level;
				break;
			case CARROTS:
				breakBlock = Material.CARROT;
				min = 1;
				max = 4 + level;
				break;
			case POTATOES:
				breakBlock = Material.POTATO;
				min = 1;
				max = 4 + level;
				break;
			case NETHER_WART:
				breakBlock = Material.NETHER_WART;
				min = 2;
				max = 4 + level;
				break;
			case SEA_LANTERN:
				breakBlock = Material.PRISMARINE_CRYSTALS;
				min = 2;
				max = 3 + level;
				actualMax = 5;
				break;
			case MELON:
				breakBlock = Material.MELON_SLICE;
				min = 3;
				max = 7 + level;
				actualMax = 9;
				break;
			case GLOWSTONE:
				breakBlock = Material.GLOWSTONE_DUST;
				min = 2;
				max = 4 + level;
				actualMax = 4;
				break;
			default:
				
			}
			int finalCount = (int) (Math.random() * (max - min)) + min;
			if(actualMax > 0 && finalCount > actualMax){
				finalCount = actualMax;
			}
			ItemStack fortunableItem = new ItemStack(breakBlock, finalCount);
			
			fortunableItem.setAmount(finalCount);
			priorItems.clear();
			priorItems.add(fortunableItem);
			if(brokenBlock.getType().equals(Material.BEETROOTS)){
				priorItems.add(new ItemStack(Material.BEETROOT));
			}else if(brokenBlock.getType().equals(Material.WHEAT)){
				priorItems.add(new ItemStack(Material.WHEAT));
			}
		}else if(brokenBlock.getType().equals(Material.GRAVEL)){
			double chance = 0;
			if(Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS) > 2){
				chance = 1;
			}else{
				if(Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS) == 1){
					chance = .14;
				}else{
					chance = .25;
				}
			}
			double random = Math.random();
			if(chance > random){
				priorItems.clear();
				priorItems.add(new ItemStack(Material.FLINT));
			}
		}else if(brokenBlock.getType() == Material.OAK_LEAVES || brokenBlock.getType() == Material.BIRCH_LEAVES 
				|| brokenBlock.getType() == Material.SPRUCE_LEAVES || brokenBlock.getType() == Material.JUNGLE_LEAVES
				|| brokenBlock.getType() == Material.DARK_OAK_LEAVES || brokenBlock.getType() == Material.ACACIA_LEAVES){
			level = Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS);
			double chance = 0;
			double appleChance = 0;
			Material sapling = null;
			switch(brokenBlock.getType()) {
			case JUNGLE_LEAVES:
				sapling = Material.JUNGLE_SAPLING;
				chance = 1.0D / 10.0D;
				switch(level) {
				case 1:
					chance = 1.0D / 36.0D;
					break;
				case 2:
					chance = 1.0D / 32.0D;
					break;
				case 3:
					chance = 1.0D / 24.0D;
					break;
				case 4:
					chance = 1.0D / 16.0D;
					break;
				}
				break;
			case OAK_LEAVES:
				if(sapling == null) {
					sapling = Material.OAK_SAPLING;
				}
			case DARK_OAK_LEAVES:
				if(sapling == null) {
					sapling = Material.DARK_OAK_SAPLING;
				}
				appleChance = 1.0D / 80.0D;
				switch(level) {
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
			case ACACIA_LEAVES:
				if(sapling == null) {
					sapling = Material.ACACIA_SAPLING;
				}
			case BIRCH_LEAVES:
				if(sapling == null) {
					sapling = Material.BIRCH_SAPLING;
				}
			case SPRUCE_LEAVES:
				if(sapling == null) {
					sapling = Material.SPRUCE_SAPLING;
				}
				chance = 1.0D / 5.0D;
				switch(level) {
				case 1:
					chance = 1.0D / 16.0D;
					break;
				case 2:
					chance = 1.0D / 12.0D;
					break;
				case 3:
					chance = 1.0D / 10.0D;
					break;
				case 4:
					chance = 1.0D / 8.0D;
					break;
				}
				break;
			default:
				break;
			
			}
			double random = Math.random();
			if(chance > random){
				priorItems.add(new ItemStack(sapling, 1));
			}
			random = Math.random();
			if(appleChance > random) {
				priorItems.add(new ItemStack(Material.APPLE, 1));
			}
		}

		return priorItems;
	}
}
