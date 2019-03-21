package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class AbilityUtilities {

	private static List<Material> FORTUNE_ITEMS = Arrays.asList(
			Material.DIAMOND_ORE, Material.EMERALD_ORE,
			Material.COAL_ORE, Material.NETHER_QUARTZ_ORE, Material.LAPIS_ORE,
			Material.REDSTONE_ORE, Material.REDSTONE_ORE, Material.WHEAT, Material.CARROTS,
			Material.POTATOES, Material.GLOWSTONE, Material.SEA_LANTERN,
			Material.MELON, Material.NETHER_WART, Material.BEETROOTS, 
			Material.GRASS, Material.GRAVEL, Material.JUNGLE_LEAVES, Material.OAK_LEAVES,
			Material.DARK_OAK_LEAVES, Material.ACACIA_LEAVES, Material.BIRCH_LEAVES, Material.SPRUCE_LEAVES);

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
		} else if (Arrays.asList(Material.REDSTONE_ORE, Material.WHEAT,
				Material.BEETROOTS, Material.CARROTS, Material.POTATOES,
				Material.NETHER_WART, Material.SEA_LANTERN, Material.MELON, Material.GLOWSTONE, Material.GRASS).contains(
				brokenBlock.getType())) {
			level = Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS);
			int min = 0;
			int max = 0;
			int actualMax = 0;
			if(brokenBlock.getState().getData() instanceof Crops) {
				Crops c = (Crops) brokenBlock.getState().getData();
	            if(!c.getState().equals(CropState.RIPE)) {
	            	return priorItems;
	            }
			} else if(brokenBlock.getState().getData() instanceof NetherWarts) {
				NetherWarts c = (NetherWarts) brokenBlock.getState().getData();
	            if(!c.getState().equals(NetherWartsState.RIPE)) {
	            	return priorItems;
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
			ItemStack fortunableItem = new ItemStack(breakBlock);
			
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
	
	public static ItemStack getSmelteryItem(Block block, ItemStack item) {
		Material material = null;
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
				material = Material.STONE;
			}
			break;
		case STONE_BRICKS:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.GOLDEN_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE).contains(item.getType())) {
				material = Material.CRACKED_STONE_BRICKS;
			}
			break;
		case NETHERRACK:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.GOLDEN_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE).contains(item.getType())) {
				material = Material.NETHER_BRICK;
			}
			break;
		case CLAY:
			material = Material.TERRACOTTA;
			break;
		case CACTUS:
			material = Material.CACTUS_GREEN;
			break;
		case OAK_LOG:
		case BIRCH_LOG:
		case SPRUCE_LOG:
		case JUNGLE_LOG:
		case DARK_OAK_LOG:
		case ACACIA_LOG:
			material = Material.CHARCOAL;
			break;
		case CHORUS_FRUIT:
			material = Material.POPPED_CHORUS_FRUIT;
			break;
		case WET_SPONGE:
			material = Material.SPONGE;
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
			return new ItemStack(material, num);
		}
		return null;
	}
	
	public static ItemStack getSilkTouchItem(Block block, ItemStack item){
		switch(block.getType()) {
		case COAL_ORE:
		case NETHER_QUARTZ_ORE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
				return new ItemStack(block.getType());
			}
			break;
		case STONE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
				return new ItemStack(block.getType());
			}
			break;
		case INFESTED_STONE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
				return new ItemStack(Material.STONE);
			}
			return null;
		case INFESTED_COBBLESTONE:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
					return new ItemStack(Material.STONE);
				}
				return new ItemStack(Material.COBBLESTONE);
			}
			return null;
		case INFESTED_STONE_BRICKS:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
					return new ItemStack(Material.CRACKED_STONE_BRICKS);
				}
				return new ItemStack(Material.STONE_BRICKS);
			}
			return null;
		case INFESTED_CRACKED_STONE_BRICKS:
			if(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, Material.GOLDEN_PICKAXE).contains(item.getType())) {
				return new ItemStack(Material.CRACKED_STONE_BRICKS);
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
		case GLASS_PANE:
		case GLOWSTONE:
		case GRASS_BLOCK:
		case MYCELIUM:
		case BROWN_MUSHROOM_BLOCK:
		case RED_MUSHROOM_BLOCK:
		case MUSHROOM_STEM:
		case MELON:
		case PODZOL:
		case SEA_LANTERN:
		case ICE:
		case PACKED_ICE:
		case BLUE_ICE:
		case SNOW_BLOCK:
		case BLACK_STAINED_GLASS:
		case BLUE_STAINED_GLASS:
		case BROWN_STAINED_GLASS:
		case CYAN_STAINED_GLASS:
		case GRAY_STAINED_GLASS:
		case GREEN_STAINED_GLASS:
		case LIGHT_BLUE_STAINED_GLASS:
		case LIGHT_GRAY_STAINED_GLASS:
		case LIME_STAINED_GLASS:
		case MAGENTA_STAINED_GLASS:
		case ORANGE_STAINED_GLASS:
		case PINK_STAINED_GLASS:
		case PURPLE_STAINED_GLASS:
		case RED_STAINED_GLASS:
		case WHITE_STAINED_GLASS:
		case YELLOW_STAINED_GLASS:
		case BLACK_STAINED_GLASS_PANE:
		case BLUE_STAINED_GLASS_PANE:
		case BROWN_STAINED_GLASS_PANE:
		case CYAN_STAINED_GLASS_PANE:
		case GRAY_STAINED_GLASS_PANE:
		case GREEN_STAINED_GLASS_PANE:
		case LIGHT_BLUE_STAINED_GLASS_PANE:
		case LIGHT_GRAY_STAINED_GLASS_PANE:
		case LIME_STAINED_GLASS_PANE:
		case MAGENTA_STAINED_GLASS_PANE:
		case ORANGE_STAINED_GLASS_PANE:
		case PINK_STAINED_GLASS_PANE:
		case PURPLE_STAINED_GLASS_PANE:
		case RED_STAINED_GLASS_PANE:
		case WHITE_STAINED_GLASS_PANE:
		case YELLOW_STAINED_GLASS_PANE:
		case BRAIN_CORAL:
		case BRAIN_CORAL_BLOCK:
		case BRAIN_CORAL_FAN:
		case BUBBLE_CORAL:
		case BUBBLE_CORAL_BLOCK:
		case BUBBLE_CORAL_FAN:
		case FIRE_CORAL:
		case FIRE_CORAL_BLOCK:
		case FIRE_CORAL_FAN:
		case HORN_CORAL:
		case HORN_CORAL_BLOCK:
		case HORN_CORAL_FAN:
		case TUBE_CORAL:
		case TUBE_CORAL_BLOCK:
		case TUBE_CORAL_FAN:
			return new ItemStack(block.getType());
		case BRAIN_CORAL_WALL_FAN:
			return new ItemStack(Material.BRAIN_CORAL_FAN);
		case BUBBLE_CORAL_WALL_FAN:
			return new ItemStack(Material.BUBBLE_CORAL_FAN);
		case FIRE_CORAL_WALL_FAN:
			return new ItemStack(Material.FIRE_CORAL_FAN);
		case HORN_CORAL_WALL_FAN:
			return new ItemStack(Material.HORN_CORAL_FAN);
		case TUBE_CORAL_WALL_FAN:
			return new ItemStack(Material.TUBE_CORAL_FAN);
		default:
			break;
		}
		return null;
	}

	public static void dropExperience(Location loc, int amount) {
		if(amount > 0) {
			((ExperienceOrb)loc.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(amount);
		}
	}
	
	public static void giveExperience(Player player, int amount) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		PlayerInventory playerInv = player.getInventory();
		for(ItemStack i : playerInv.getArmorContents()) {
			if(i != null && Enchantments.hasEnchantment(i, Enchantment.MENDING)) {
				items.add(i);
			}
		}
		if(playerInv.getItemInMainHand() != null && Enchantments.hasEnchantment(playerInv.getItemInMainHand(), Enchantment.MENDING)) {
			items.add(playerInv.getItemInMainHand());
		}
		if(playerInv.getItemInOffHand() != null && Enchantments.hasEnchantment(playerInv.getItemInOffHand(), Enchantment.MENDING)) {
			items.add(playerInv.getItemInOffHand());
		}
		
		if(items.size() > 0) {
			Collections.shuffle(items);
			ItemStack item = items.get(0);
			int durability = DamageUtils.getDamage(item.getItemMeta());
			while(amount > 0 && durability > 0) {
				durability -= 2;
				amount--;
			}
			if(durability < 0) durability = 0;
			DamageUtils.setDamage(item, durability);
			if(amount > 0) {
				player.giveExp(amount);
			}
		} else {
			player.giveExp(amount);
		}
	}
}
