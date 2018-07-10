package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class AbilityUtilities {

	@SuppressWarnings("deprecation")
	private static List<Material> FORTUNE_ITEMS = Arrays.asList(
			Material.DIAMOND_ORE, Material.EMERALD_ORE,
			Material.COAL_ORE, Material.NETHER_QUARTZ_ORE, Material.LAPIS_ORE,
			Material.REDSTONE_ORE, Material.REDSTONE_ORE, Material.LEGACY_CROPS, Material.CARROT,
			Material.POTATO, Material.GLOWSTONE, Material.SEA_LANTERN,
			Material.MELON_BLOCK, Material.LEGACY_NETHER_STALK,
			Material.LEGACY_BEETROOT_BLOCK, Material.LEGACY_LONG_GRASS, Material.GRAVEL, Material.LEGACY_LEAVES, Material.LEGACY_LEAVES_2);

	public static Collection<ItemStack> getFortuneItems(ItemStack item,
			Block brokenBlock, Collection<ItemStack> priorItems) {
		boolean ironGold = false;
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
					ItemStack smelted = getSmelteryItem(brokenBlock, item);
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
				int level = Enchantments.getLevel(item,
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
		} else if (Arrays.asList(Material.REDSTONE_ORE, Material.LEGACY_CROPS,
				Material.LEGACY_BEETROOT_BLOCK, Material.LEGACY_CARROT, Material.LEGACY_POTATO,
				Material.LEGACY_NETHER_STALK, Material.SEA_LANTERN, Material.MELON_BLOCK, Material.GLOWSTONE, Material.LEGACY_LONG_GRASS).contains(
				brokenBlock.getType())) {
			int level = Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS);
			int min = 0;
			int max = 0;
			int actualMax = 0;
			Material breakBlock = null;
			switch(brokenBlock.getType()){
			case REDSTONE_ORE:
				if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE).contains(item.getType())) {
					min = 4;
					max = 5 + level;
					breakBlock = Material.REDSTONE;
				}
				break;
			case LEGACY_LONG_GRASS:
				for(ItemStack priorItem : priorItems) {
					if(priorItem.getType().equals(Material.WHEAT_SEEDS)){
						min = 1;
						max = 3 + level * 2;
						break;
					}
				}
				breakBlock = Material.WHEAT_SEEDS;
				break;
			case LEGACY_CROPS:
				breakBlock = Material.WHEAT_SEEDS;
				min = 0;
				max = 3 + level;
				break;
			case LEGACY_BEETROOT_BLOCK:
				breakBlock = Material.BEETROOT_SEEDS;
				min = 0;
				max = 3 + level;
				break;
			case LEGACY_CARROT:
				breakBlock = Material.LEGACY_CARROT_ITEM;
				min = 1;
				max = 4 + level;
				break;
			case LEGACY_POTATO:
				breakBlock = Material.LEGACY_POTATO_ITEM;
				min = 1;
				max = 4 + level;
				break;
			case LEGACY_NETHER_STALK:
				breakBlock = Material.LEGACY_NETHER_WARTS;
				min = 2;
				max = 4 + level;
				break;
			case SEA_LANTERN:
				breakBlock = Material.PRISMARINE_CRYSTALS;
				min = 2;
				max = 3 + level;
				actualMax = 5;
				break;
			case MELON_BLOCK:
				breakBlock = Material.MELON;
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
			ItemStack fortunableItem = new ItemStack(breakBlock);
			
			fortunableItem.setAmount(finalCount);
			priorItems.clear();
			priorItems.add(fortunableItem);
			if(brokenBlock.getType().equals(Material.LEGACY_BEETROOT_BLOCK)){
				priorItems.add(new ItemStack(Material.BEETROOT));
			}else if(brokenBlock.getType().equals(Material.LEGACY_CROPS)){
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
		}else if(brokenBlock.getType().equals(Material.LEGACY_LEAVES) || brokenBlock.getType().equals(Material.LEGACY_LEAVES_2)){
			TreeType type = TreeType.get(brokenBlock);
			int level = Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS);
			if(type.equals(TreeType.JUNGLE)){
				double chance = 0;
				if(4 * (level - 1) * (level - 1) >= 36){
					chance = .25;
				}else{
					chance = 1.0 / (36 - 4 * (level - 1) * (level - 1));
				}
				if(chance > .25) chance = .25;
				double random = Math.random();
				if(chance > random){
					priorItems.add(type.getSapling(1));
				}
			}else{
				double chance = 0;
				if(4 * (level - 1) * (level - 1) >= 16){
					chance = .50;
				}else{
					chance = 1.0 / (16 - 4 * (level - 1) * (level - 1));
				}
				if(chance > .50) chance = .50;
				double random = Math.random();
				if(chance > random){
					priorItems.add(type.getSapling(1));
				}
				if(type.equals(TreeType.DARK_OAK) || type.equals(TreeType.OAK)){
					chance = 0;
					if(20 * (level - 1) * (level - 1) >= 16){
						chance = .50;
					}else{
						chance = 1.0 / (180 - 20 * (level - 1) * (level - 1));
					}
					if(chance > .50) chance = .50;
					random = Math.random();
					if(chance > random){
						priorItems.add(type.getSapling(1));
					}
				}
			}
		}

		return priorItems;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getSmelteryItem(Block block, ItemStack item) {
		Material material = null;
		int data = 0;
		boolean fortune = false;
		switch(block.getType()) {
		case IRON_ORE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE).contains(item.getType())) {
				material = Material.IRON_INGOT;
				fortune = true;
			}
			break;
		case GOLD_ORE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE).contains(item.getType())) {
				material = Material.GOLD_INGOT;
				fortune = true;
			}
			break;
		case SAND:
			material = Material.GLASS;
			break;
		case COBBLESTONE:
		case STONE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.GOLDEN_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE).contains(item.getType())) {
				if(block.getData() == 0) {
					material = Material.STONE;
				}
			}
			break;
		case LEGACY_SMOOTH_BRICK:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.GOLDEN_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE).contains(item.getType())) {
				if(block.getData() == 0) {
					material = Material.LEGACY_SMOOTH_BRICK;
					data = 2;
				}
			}
			break;
		case NETHERRACK:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.GOLDEN_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE).contains(item.getType())) {
				material = Material.LEGACY_NETHER_BRICK_ITEM;
			}
			break;
		case CLAY:
			material = Material.LEGACY_HARD_CLAY;
			break;
		case CACTUS:
			material = Material.LEGACY_INK_SACK;
			data = 2;
			break;
		case LEGACY_LOG:
		case LEGACY_LOG_2:
			material = Material.COAL;
			data = 1;
			break;
		case CHORUS_FRUIT:
			material = Material.CHORUS_FRUIT_POPPED;
			break;
		case SPONGE:
			if(block.getData() == 1) {
				material = Material.SPONGE;
			}
			break;
		default:
			
		}
		int num = 1;
		if(fortune && Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			int level = Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS) + 2;
			int multiply = (int) (Math.random() * level);
			if(multiply > 1) {
				num *= multiply;
			}
		}
		if(material != null) {
			return new ItemStack(material, num, (byte) data);
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getSilkTouchItem(Block block, ItemStack item){
		switch(block.getType()) {
		case COAL_ORE:
		case NETHER_QUARTZ_ORE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
				return new ItemStack(block.getType());
			}
			break;
		case STONE:
			if(block.getData() == 0) {
				if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
					return new ItemStack(block.getType());
				}
			}
			break;
		case LEGACY_MONSTER_EGGS:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
				if(block.getData() == 0) {
					return new ItemStack(Material.STONE);
				}
				if(block.getData() == 1) {
					return new ItemStack(Material.COBBLESTONE);
				}
				if(block.getData() >= 2 && block.getData() <= 5) {
					return new ItemStack(Material.LEGACY_SMOOTH_BRICK, 1, (byte) (block.getData() - 2));
				}
			}
			return null;
		case LAPIS_ORE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE).contains(item.getType())) {
				return new ItemStack(block.getType());
			}
			break;
		case EMERALD_ORE:
		case DIAMOND_ORE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE).contains(item.getType())) {
				return new ItemStack(block.getType());
			}
			break;
		case REDSTONE_ORE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE).contains(item.getType())) {
				return new ItemStack(Material.REDSTONE_ORE);
			}
			break;
		case BOOKSHELF:
		case CLAY:
		case ENDER_CHEST:
		case GLASS:
		case LEGACY_THIN_GLASS:
		case GLOWSTONE:
		case GRASS:
		case LEGACY_MYCEL:
		case LEGACY_HUGE_MUSHROOM_1:
		case LEGACY_HUGE_MUSHROOM_2:
		case MELON_BLOCK:
		case SEA_LANTERN:
		case ICE:
		case PACKED_ICE:
		case SNOW_BLOCK:
			return new ItemStack(block.getType());
		case LEGACY_STAINED_GLASS:
		case LEGACY_STAINED_GLASS_PANE:
			return new ItemStack(block.getType(), 1, block.getData());
		case LEGACY_LEAVES:
		case LEGACY_LEAVES_2:
			return new ItemStack(block.getType(), 1, (byte)(block.getData() % 4));
		case DIRT:
			if(block.getData() == 2) {
				return new ItemStack(block.getType(), 1, block.getData());
			}
		default:
			break;
		}
		return null;
	}
}
