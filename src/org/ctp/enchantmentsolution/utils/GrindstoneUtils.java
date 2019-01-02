package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AnvilUtils.RepairType;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class GrindstoneUtils {
	
	public static boolean canCombineItems(ItemStack first, ItemStack second) {
		if(first.getType() == second.getType()) {
			return true;
		}
		return false;
	}
	
	public static ItemStack combineItems(Player player, ItemStack first, ItemStack second) {
		ItemStack combined = new ItemStack(first.getType());
		if(first.getType().equals(Material.ENCHANTED_BOOK)) {
			combined = new ItemStack(Material.BOOK);
		}
		
		if(first.getType() != Material.BOOK && first.getType() != Material.ENCHANTED_BOOK && ItemType.hasItemType(first.getType())) {
			DamageUtils.setDamage(combined, DamageUtils.getDamage(first.getItemMeta()));
			RepairType repairType = RepairType.getRepairType(first, second);
			if(repairType == null) {
				return null;
			}
			int extraDurability = second.getType().getMaxDurability() - DamageUtils.getDamage(second.getItemMeta()) + (int) (second.getType().getMaxDurability() * .05);
			DamageUtils.setDamage(combined, DamageUtils.getDamage(first.getItemMeta()) - extraDurability);
			if(DamageUtils.getDamage(combined.getItemMeta()) < 0) {
				DamageUtils.setDamage(combined, 0);
			}
		} else {
			combined.setAmount(first.getAmount() + second.getAmount());
		}
		
		ItemMeta firstMeta = first.getItemMeta();
		ItemMeta combinedMeta = combined.getItemMeta();
		
		Enchantments.addEnchantmentsToItem(combined, combineEnchants(player, first, second));
		
		combinedMeta.setDisplayName(firstMeta.getDisplayName());
		combinedMeta.setLocalizedName(firstMeta.getLocalizedName());
		
		combined.setItemMeta(combinedMeta);
		
		return combined;
	}
	
	public static int getExperience(ItemStack first, ItemStack second) {
		return 0;
	}
	
	public static ItemStack combineItems(Player player, ItemStack first) {
		ItemStack combined = new ItemStack(first.getType());
		if(first.getType().equals(Material.ENCHANTED_BOOK)) {
			combined = new ItemStack(Material.BOOK);
		}
		
		DamageUtils.setDamage(combined, DamageUtils.getDamage(first.getItemMeta()));
		
		Enchantments.addEnchantmentsToItem(combined, combineEnchants(player, first, null));
		
		ItemMeta firstMeta = first.getItemMeta();
		ItemMeta combinedMeta = combined.getItemMeta();
		
		combinedMeta.setDisplayName(firstMeta.getDisplayName());
		combinedMeta.setLocalizedName(firstMeta.getLocalizedName());
		
		combined.setItemMeta(combinedMeta);
		
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
					if(Enchantments.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant) && customEnchant.isCurse()) {
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
				if(Enchantments.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant) && customEnchant.isCurse()) {
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
