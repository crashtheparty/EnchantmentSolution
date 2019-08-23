package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class GrindstoneUtils {
	
	public static boolean canCombineItems(ItemStack first, ItemStack second) {
		if(first.getType() == second.getType()) {
			return true;
		}
		return false;
	}
	
	public static boolean canTakeEnchantments(ItemStack first, ItemStack second) {
		if(first.getType() != Material.BOOK && first.getType() != Material.ENCHANTED_BOOK && first.hasItemMeta() && first.getItemMeta().hasEnchants()) {
			if(second.getType() == Material.BOOK && (second.hasItemMeta() && !second.getItemMeta().hasEnchants()) || !second.hasItemMeta()) {
				return true;
			}
		}
		return false;
	}
	
	public static ItemStack takeEnchantments(Player player, ItemStack first, ItemStack second) {
		ItemStack take = new ItemStack(second.getType());
		if(take.getType() == Material.BOOK && ConfigUtils.getEnchantedBook()) {
			take = Enchantments.convertToEnchantedBook(take);
		} else if (take.getType() == Material.ENCHANTED_BOOK && !ConfigUtils.getEnchantedBook()) {
			take = Enchantments.convertToRegularBook(take);
		}
		
		List<EnchantmentLevel> enchantments = new ArrayList<EnchantmentLevel>();
		for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = first.getEnchantments().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			for(CustomEnchantment ench : Enchantments.getEnchantments()) {
				if(ench.getRelativeEnchantment().equals(e.getKey())) {
					enchantments.add(new EnchantmentLevel(ench, e.getValue()));
				}
			}
		}
		
		take = Enchantments.addEnchantmentsToItem(take, enchantments);
		return take;
	}
	
	public static int getEnchantmentCost(ItemStack item) {
		int cost = 0;
		ItemMeta itemMeta = item.clone().getItemMeta();
		Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
		for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = enchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			int level = e.getValue();
			for(CustomEnchantment customEnchant : Enchantments.getEnchantments()) {
				if(ConfigUtils.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant)) {
					cost += level * customEnchant.multiplier(item.getType());
				}
			}
		}
		return Math.max(cost / ConfigUtils.getLevelDivisor(), 1);
	}
	
	public static int getExperience(ItemStack itemOne, ItemStack itemTwo) {
		Random random = new Random();
        byte b0 = 0;
        int j = b0;
        
        if(itemOne != null) {
        	j += getEnchantmentExperience(itemOne);
        }
        if(itemTwo != null) {
            j += getEnchantmentExperience(itemTwo);
        }
        
        if (j > 0) {
            int k = (int) Math.ceil(j / 2.0D);
            return k + random.nextInt(k);
        } else {
            return 0;
        }
	}

    private static int getEnchantmentExperience(ItemStack itemstack) {
        int j = 0;
		Map<Enchantment, Integer> map = itemstack.getItemMeta().getEnchants();
		if(itemstack.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemstack.getItemMeta();
			map = meta.getStoredEnchants();
		}
        Iterator<Entry<Enchantment, Integer>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<Enchantment, Integer> entry = iterator.next();
            Enchantment enchantment = entry.getKey();
            Integer integer = entry.getValue();
            
            CustomEnchantment custom = DefaultEnchantments.getCustomEnchantment(enchantment);
            
            if (!DefaultEnchantments.getCustomEnchantment(enchantment).isCurse()) {
                j += custom.enchantability(integer);
            }
        }

        return j;
    }
	
	public static ItemStack combineItems(Player player, ItemStack first, ItemStack second) {
		ItemStack combined = new ItemStack(first.getType());
		if(first.getType().equals(Material.ENCHANTED_BOOK)) {
			combined = new ItemStack(Material.BOOK);
		}
		
		if(first.getType() != Material.BOOK && first.getType() != Material.ENCHANTED_BOOK && ItemType.hasItemType(first.getType())) {
			DamageUtils.setDamage(combined, DamageUtils.getDamage(first.getItemMeta()));
			int extraDurability = second.getType().getMaxDurability() - DamageUtils.getDamage(second.getItemMeta()) + (int) (second.getType().getMaxDurability() * .05);
			DamageUtils.setDamage(combined, DamageUtils.getDamage(first.getItemMeta()) - extraDurability);
			if(DamageUtils.getDamage(combined.getItemMeta()) < 0) {
				DamageUtils.setDamage(combined, 0);
			}
		} else {
			combined.setAmount(1);
		}
		
		ItemMeta firstMeta = first.getItemMeta();
		ItemMeta combinedMeta = combined.getItemMeta();
		
		combinedMeta.setDisplayName(firstMeta.getDisplayName());
		
		combined.setItemMeta(combinedMeta);
		
		Enchantments.addEnchantmentsToItem(combined, combineEnchants(player, first, second));
		
		return combined;
	}
	
	public static ItemStack combineItems(Player player, ItemStack first) {
		ItemStack combined = new ItemStack(first.getType());
		if(first.getType().equals(Material.ENCHANTED_BOOK)) {
			combined = new ItemStack(Material.BOOK);
		}
		
		DamageUtils.setDamage(combined, DamageUtils.getDamage(first.getItemMeta()));
		
		ItemMeta firstMeta = first.getItemMeta();
		ItemMeta combinedMeta = combined.getItemMeta();
		
		combinedMeta.setDisplayName(firstMeta.getDisplayName());
		
		combined.setItemMeta(combinedMeta);
		
		Enchantments.addEnchantmentsToItem(combined, combineEnchants(player, first, null));
		
		return combined;
	}
	
	public static List<EnchantmentLevel> combineEnchants(Player player, ItemStack first, ItemStack second){
		ItemMeta firstMeta = first.clone().getItemMeta();
		Map<Enchantment, Integer> firstEnchants = firstMeta.getEnchants();
		if(first.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) firstMeta;
			firstEnchants = meta.getStoredEnchants();
		}
		List<EnchantmentLevel> enchantments = new ArrayList<EnchantmentLevel>();
		if(second != null) {
			ItemMeta secondMeta = second.clone().getItemMeta();
			Map<Enchantment, Integer> secondEnchants = secondMeta.getEnchants();
			if(second.getType().equals(Material.ENCHANTED_BOOK)) {
				EnchantmentStorageMeta meta = (EnchantmentStorageMeta) secondMeta;
				secondEnchants = meta.getStoredEnchants();
			}
			
			for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = secondEnchants.entrySet().iterator(); it.hasNext();) {
				java.util.Map.Entry<Enchantment, Integer> e = it.next();
				Enchantment enchant = e.getKey();
				int level = e.getValue();
				for(CustomEnchantment customEnchant : Enchantments.getEnchantments()) {
					boolean added = false;
					if(ConfigUtils.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant) && customEnchant.isCurse()) {
						for(EnchantmentLevel enchantment : enchantments) {
							if(customEnchant.getRelativeEnchantment().equals(enchantment.getEnchant().getRelativeEnchantment())) {
								added = true; 
								break;
							}
						}
						if(!added) {
							enchantments.add(new EnchantmentLevel(customEnchant, level));
						}
					}
				}
			}
			
		}
			
		for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = firstEnchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			int level = e.getValue();
			for(CustomEnchantment customEnchant : Enchantments.getEnchantments()) {
				boolean added = false;
				if(ConfigUtils.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant) && customEnchant.isCurse()) {
					for(EnchantmentLevel enchantment : enchantments) {
						if(customEnchant.getRelativeEnchantment().equals(enchantment.getEnchant().getRelativeEnchantment())) {
							added = true; 
							break;
						}
					}
					if(!added) {
						enchantments.add(new EnchantmentLevel(customEnchant, level));
					}
				}
			}
		}
		
		for(int i = enchantments.size() - 1; i >= 0; i--) {
			EnchantmentLevel enchant = enchantments.get(i);
			if(!enchant.getEnchant().canAnvil(player, enchant.getLevel())) {
				int level = enchant.getEnchant().getAnvilLevel(player, enchant.getLevel());
				if(level > 0) {
					enchantments.get(i).setLevel(level);
				} else {
					enchantments.remove(i);
				}
			}
		}
		
		return enchantments;
	}
}
