package org.ctp.enchantmentsolution.utils.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.events.SmelteryEvent;
import org.ctp.enchantmentsolution.utils.items.ItemBreakType;
import org.ctp.enchantmentsolution.utils.items.helpers.FortuneLeaves;
import org.ctp.enchantmentsolution.utils.items.helpers.FortuneMaterial;

public class Fortune {
	private static List<String> FORTUNE_ITEMS = Arrays.asList(
			"DIAMOND_ORE", "EMERALD_ORE", "COAL_ORE", "NETHER_QUARTZ_ORE", "LAPIS_ORE", "REDSTONE_ORE", "REDSTONE_ORE", "WHEAT", "CARROTS",
			"POTATOES", "GLOWSTONE", "SEA_LANTERN", "MELON", "NETHER_WART", "BEETROOTS", "GRASS", "GRAVEL", "JUNGLE_LEAVES", "OAK_LEAVES",
			"DARK_OAK_LEAVES", "ACACIA_LEAVES", "BIRCH_LEAVES", "SPRUCE_LEAVES");
	private static List<String> CROPS = Arrays.asList("WHEAT", "CARROTS", "POTATOES", "NETHER_WART", "BEETROOTS");
	private static List<String> SIMPLE_MULTIPLY = Arrays.asList("DIAMOND_ORE", "EMERALD_ORE", "COAL_ORE", "NETHER_QUARTZ_ORE", "LAPIS_ORE");
	
	private static List<FortuneMaterial> INCREASE_TO_MAX = Arrays.asList(new FortuneMaterial("REDSTONE_ORE", "REDSTONE", 4, 5), 
			new FortuneMaterial("WHEAT", "WHEAT_SEEDS", 0, 3), new FortuneMaterial("BEETROOTS", "BEETROOT_SEEDS", 0, 3), 
			new FortuneMaterial("CARROTS", "CARROT", 1, 4), new FortuneMaterial("POTATOES", "POTATO", 1, 4), new FortuneMaterial("NETHER_WART", 2, 4), 
			new FortuneMaterial("SEA_LANTERN", "PRISMARINE_CRYSTALS", 2, 3, 5), new FortuneMaterial("MELON", "MELON_SLICE", 3, 7, 9),
			new FortuneMaterial("GLOWSTONE", "GLOWSTONE_DUST", 2, 4, 4), new FortuneMaterial("GRASS", "WHEAT_SEEDS", 1, 3));
	
	private static List<String> LEAVES = Arrays.asList("ACACIA_LEAVES", "BIRCH_LEAVES", "DARK_OAK_LEAVES", "JUNGLE_LEAVES", "OAK_LEAVES", "SPRUCE_LEAVES");

	public static Collection<ItemStack> getFortuneItems(Player player, ItemStack item,
			Block brokenBlock, Collection<ItemStack> priorItems, boolean applySmeltery) {
		boolean ironGold = false;
		int level = Enchantments.getLevel(item,
				Enchantment.LOOT_BONUS_BLOCKS);
		if(level <= 0) return priorItems;
		Iterator<ItemStack> iter = priorItems.iterator();
		List<ItemStack> duplicate = new ArrayList<ItemStack>();
		while(iter.hasNext()) {
			duplicate.add(iter.next());
		}
		
		if (applySmeltery && !(FORTUNE_ITEMS.contains(brokenBlock.getType().name()))) {
			boolean returnAfterApplying = true;
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY) && DefaultEnchantments.isEnabled(DefaultEnchantments.SMELTERY)) {
				ItemStack smelted = Smeltery.getSmelteryItem(brokenBlock, item);
				if(smelted != null) {
					SmelteryEvent event = new SmelteryEvent(player, item, brokenBlock, smelted, true);
					Bukkit.getPluginManager().callEvent(event);
					if(!event.isCancelled()) {
						priorItems.clear();
						priorItems.add(event.getDrop());
						if(event.getBlock().getType() == Material.IRON_ORE || event.getBlock().getType() == Material.GOLD_ORE) {
							ironGold = true;
							returnAfterApplying = false;
						}
					}
				}
			}
			if(returnAfterApplying) {
				return priorItems;
			}
		}

		if (SIMPLE_MULTIPLY.contains(brokenBlock.getType().name()) || ironGold) {
			boolean canBreak = false;
			ItemBreakType type = ItemBreakType.getType(item.getType());
			if(SIMPLE_MULTIPLY.contains(brokenBlock.getType().name()) && type.getBreakTypes().contains(brokenBlock.getType())) {
				canBreak = true;
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
		} else if (inIncreaseToMax(brokenBlock.getType())) {
			level = Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS);
			if(brokenBlock.getBlockData() instanceof Ageable) {
				Ageable age = (Ageable) brokenBlock.getBlockData();
				if(CROPS.contains(brokenBlock.getType().name())) {
					if(age.getAge() != age.getMaximumAge()) {
						return priorItems;
					}
				}
			}
			FortuneMaterial material = null;
			for(FortuneMaterial mat : INCREASE_TO_MAX) {
				if(mat.getMaterial() == brokenBlock.getType()) {
					material = mat;
					break;
				}
			}
			if(material != null && material.getMaterial() != null && material.getBreakMaterial() != null) {
				int finalCount = (int) (Math.random() * (material.getMax() * (material.getMaterialName().equals("GRASS") ? 2 : 1)
						- material.getMin())) + material.getMin();
				if(material.getActualMax() > 0 && finalCount > material.getActualMax()){
					finalCount = material.getActualMax();
				}
				ItemStack fortunableItem = new ItemStack(material.getBreakMaterial(), finalCount);
				fortunableItem.setAmount(finalCount);
				priorItems.clear();
				priorItems.add(fortunableItem);
				if(brokenBlock.getType().equals(Material.BEETROOTS)){
					priorItems.add(new ItemStack(Material.BEETROOT));
				}else if(brokenBlock.getType().equals(Material.WHEAT)){
					priorItems.add(new ItemStack(Material.WHEAT));
				}
			}
		} else if (brokenBlock.getType().equals(Material.GRAVEL)) {
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
		}else if(LEAVES.contains(brokenBlock.getType().name())){
			level = Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS);
			FortuneLeaves leaves = null;
			try {
				leaves = FortuneLeaves.valueOf(brokenBlock.getType().name());
			} catch (Exception ex) {
				
			}
			if(leaves != null) {
				double random = Math.random();
				if(leaves.getSaplingChance(level) > random && leaves.getSapling() != null) {
					priorItems.add(new ItemStack(leaves.getSapling()));
				}
				random = Math.random();
				if(leaves.getAppleChance(level) > random) {
					priorItems.add(new ItemStack(Material.APPLE, 1));
				}
			}
		}

		return priorItems;
	}
	
	private static boolean inIncreaseToMax(Material material) {
		for(FortuneMaterial mat : INCREASE_TO_MAX) {
			if(mat.getMaterial() == material) return true;
		}
		return false;
	}
}
