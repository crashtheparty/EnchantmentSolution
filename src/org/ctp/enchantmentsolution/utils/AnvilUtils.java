package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.LegacyAnvil;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemRepairType;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class AnvilUtils {
	
	private static List<Player> OPEN_LEGACY = new ArrayList<Player>();
	
	public enum RepairType{
		RENAME,
		REPAIR,
		COMBINE;
		
		public static RepairType getRepairType(ItemStack first, ItemStack second) {
			if(second == null) {
				return RepairType.RENAME;
			}
			if(canCombineItems(first, second)) {
				if(ItemUtils.getRepairMaterials().contains(second.getType())) {
					return RepairType.REPAIR;
				}else {
					return RepairType.COMBINE;
				}
			}
			return null;
		}
	}

	public static boolean canCombineItems(ItemStack first, ItemStack second) {
		if(ItemRepairType.getType(first.getType()) == null) return false;
		List<Material> items = ItemRepairType.getType(first.getType()).getRepairTypes();
		if(items == null) {
			return false;
		}
		if(second.getItemMeta().getEnchants().size() > 0 || DamageUtils.getDamage(first.getItemMeta()) > 0) {
			if(items.contains(second.getType())) {
				if((!second.getType().equals(Material.BOOK) && !second.getType().equals(Material.ENCHANTED_BOOK)) || DamageUtils.getDamage(first.getItemMeta()) > 0 || !second.getType().equals(first.getType())) {
					return true;
				}
				if(second.getType().equals(Material.ENCHANTED_BOOK)) {
					Map<Enchantment, Integer> enchantments = ((EnchantmentStorageMeta) second.getItemMeta()).getStoredEnchants();
					return checkEnchantments(enchantments, first);
				}
				Map<Enchantment, Integer> enchantments = second.getItemMeta().getEnchants();
				return checkEnchantments(enchantments, first);
			}
		}else if(second.getType().equals(Material.ENCHANTED_BOOK)) {
			if(((EnchantmentStorageMeta) second.getItemMeta()).getStoredEnchants().size() > 0) {
				Map<Enchantment, Integer> enchantments = ((EnchantmentStorageMeta) second.getItemMeta()).getStoredEnchants();
				return checkEnchantments(enchantments, first);
			}
		}
		return false;
	}
	
	private static boolean checkEnchantments(Map<Enchantment, Integer> enchantments, ItemStack first) {
		for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = enchantments.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			for(CustomEnchantment customEnchant : Enchantments.getEnchantments()) {
				if(customEnchant.getRelativeEnchantment().equals(enchant)) {
					if(Enchantments.canAddEnchantment(customEnchant, first)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void addLegacyAnvil(Player player) {
		OPEN_LEGACY.add(player);
	}
	
	public static boolean hasLegacyAnvil(Player player) {
		for(Player p : OPEN_LEGACY) {
			if(p.equals(player)) {
				return true;
			}
		}
		return false;
	}
	
	public static void removeLegacyAnvil(LegacyAnvil anvil) {
		OPEN_LEGACY.remove(anvil.getPlayer());
	}
	
	public static void checkAnvilBreak(Player player, Block block, Anvil anvil) {
		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
			return;
		}
		double chance = .12;
		double roll = Math.random();
		if(chance > roll) {
			Material material = Material.AIR;
			switch(block.getType()) {
			case ANVIL:
				material = Material.CHIPPED_ANVIL;
				break;
			case CHIPPED_ANVIL:
				material = Material.DAMAGED_ANVIL;
				break;
			default:
				
			}
			if(material == Material.AIR) {
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
				if(anvil != null) {
					anvil.close(false);
				}
				block.setType(material);
			} else {
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
				BlockFace facing = ((Directional) block.getBlockData()).getFacing();
				block.setType(material);
				Directional d = (Directional) block.getBlockData();
				d.setFacing(facing);
				block.setBlockData(d);
			}
		} else {
			block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
		}
	}
}
