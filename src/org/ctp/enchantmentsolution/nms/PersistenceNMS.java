package org.ctp.enchantmentsolution.nms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.CrashAPI;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.nms.persistence.LegacyPersistenceUtils;
import org.ctp.enchantmentsolution.nms.persistence.PersistenceUtils;

public class PersistenceNMS {

	public static String getEnchantmentString(EnchantmentLevel level) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) return PersistenceUtils.getEnchantmentString(level);
		return LegacyPersistenceUtils.getEnchantmentString(level);
	}

	public static EnchantmentLevel getEnchantment(String s, ItemMeta meta) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) return PersistenceUtils.returnEnchantmentLevel(s, meta);
		return LegacyPersistenceUtils.getEnchantment(s);
	}

	public static boolean isEnchantment(ItemMeta meta, String s) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) return PersistenceUtils.isEnchantment(meta, s).isEnchant();
		return LegacyPersistenceUtils.isEnchantment(s);
	}

	public static EnchantmentLevel returnEnchantmentLevel(String s, ItemMeta meta) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) return PersistenceUtils.returnEnchantmentLevel(s, meta);
		return LegacyPersistenceUtils.returnEnchantmentLevel(s, meta);
	}

	public static String returnEnchantmentName(EnchantmentLevel level) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) return PersistenceUtils.returnEnchantmentName(level);
		return LegacyPersistenceUtils.returnEnchantmentName(level.getEnchant(), level.getLevel());
	}

	public static String returnEnchantmentName(CustomEnchantment enchant, int level) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) return PersistenceUtils.returnEnchantmentName(new EnchantmentLevel(enchant, level));
		return LegacyPersistenceUtils.returnEnchantmentName(enchant, level);
	}

	public static void removeEnchantment(ItemStack item, CustomEnchantment enchant) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) PersistenceUtils.removePersistence(item, enchant);
		else {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			if (lore == null) lore = new ArrayList<String>();
			lore = LegacyPersistenceUtils.removeEnchantment(enchant, lore);
			meta.setLore(lore);
			item.setItemMeta(meta);
			setLore(item);
		}
	}

	public static boolean isLegacyEnchantment(String s) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() <= 11) LegacyPersistenceUtils.isLegacyEnchantment(s);
		return false;
	}

	public static boolean addEnchantments(ItemStack item, List<EnchantmentLevel> levels) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) return PersistenceUtils.addPersistence(item, levels);
		else {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			if (lore == null) lore = new ArrayList<String>();
			for(EnchantmentLevel level: levels)
				lore.add(LegacyPersistenceUtils.getEnchantmentString(level));
			meta.setLore(lore);
			item.setItemMeta(meta);
			return true;
		}
	}

	public static boolean addEnchantment(ItemStack item, EnchantmentLevel level) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) return addEnchantments(item, Arrays.asList(level));
		else {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			if (lore == null) lore = new ArrayList<String>();
			lore.add(LegacyPersistenceUtils.getEnchantmentString(level));
			meta.setLore(lore);
			item.setItemMeta(meta);
			return true;
		}

	}

	public static void setLore(ItemStack item) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) PersistenceUtils.setItemLore(item);
		else {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			if (lore == null) lore = new ArrayList<String>();
			meta = LegacyPersistenceUtils.setLore(meta, lore);
			item.setItemMeta(meta);
		}
	}

	public static boolean isStickyHold(ItemStack item) {
		boolean stickyHold = false;
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) stickyHold = PersistenceUtils.isStickyHold(item);
		if (!stickyHold && CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() <= 11) stickyHold = LegacyPersistenceUtils.isStickyHold(item);
		return stickyHold;
	}

	public static Material stickyItemType(ItemStack item) {
		Material type = null;
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) type = PersistenceUtils.stickyItemType(item);
		if (type == null && CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() <= 11) type = LegacyPersistenceUtils.stickyItemType(item);
		return type;
	}

	public static ItemStack repairStickyHold(ItemStack item) {
		ItemStack stickyItem = null;
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) stickyItem = PersistenceUtils.repairStickyHold(item);
		if (stickyItem == null && CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() <= 11) stickyItem = LegacyPersistenceUtils.repairStickyHold(item);
		return stickyItem;
	}

	public static ItemStack createStickyHold(ItemStack stickyItem) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) return PersistenceUtils.createStickyHold(stickyItem);
		else
			return LegacyPersistenceUtils.createStickyHold(stickyItem);
	}

	public static List<Integer> getAnimalIDsFromItem(ItemStack item) {
		List<Integer> ids = new ArrayList<Integer>();
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) ids = PersistenceUtils.getAnimalIDsFromItem(item);
		if (ids.size() == 0 && CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() <= 11) ids = LegacyPersistenceUtils.getAnimalIDsFromItem(item);
		return ids;
	}

	public static void removeAnimal(ItemStack item, int id) {
		boolean remove = false;
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) remove = PersistenceUtils.removeAnimal(item, id);
		if (!remove && CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() <= 11) LegacyPersistenceUtils.removeAnimal(item, id);
	}

	public static void addAnimal(ItemStack item, int id) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) PersistenceUtils.addAnimal(item, id);
		else
			LegacyPersistenceUtils.addAnimal(item, id);
	}

	public static boolean checkItem(ItemStack original) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 11) return PersistenceUtils.checkItem(original);
		else
			return LegacyPersistenceUtils.checkItem(original) != null;
	}

}
