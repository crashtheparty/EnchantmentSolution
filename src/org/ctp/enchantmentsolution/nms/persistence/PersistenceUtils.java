package org.ctp.enchantmentsolution.nms.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.MatData;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class PersistenceUtils {

	private static PersistentDataType<String, String> t = PersistentDataType.STRING;
	private static PersistentDataType<String, List<Integer>> li = new PersistentStringListInt();
	private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

	public static void addPersistence(ItemStack item, List<EnchantmentLevel> levels) {
		ItemMeta meta = item.getItemMeta();
		if (meta.getLore() == null) meta.setLore(new ArrayList<String>());
		PersistentDataContainer container = meta.getPersistentDataContainer();
		List<String> lore = meta.getLore();
		for(EnchantmentLevel level: levels) {
			container.set(EnchantmentSolution.getKey(level.getEnchant().getName()), t, level.getEnchant().getDisplayName());
			container.set(EnchantmentSolution.getKey(level.getEnchant().getName() + "_level"), t, ((Integer) level.getLevel()).toString());
			String loreName = container.get(EnchantmentSolution.getKey(level.getEnchant().getName()), t);
			Iterator<String> iter = lore.iterator();
			while (iter.hasNext()) {
				String l = iter.next();
				if (ChatColor.stripColor(l).startsWith(loreName)) iter.remove();
			}
			lore.add(returnEnchantmentName(level));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		setItemLore(item);
	}

	public static void removePersistence(ItemStack item, CustomEnchantment enchant) {
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		String oldDisplayName = container.get(EnchantmentSolution.getKey(enchant.getName()), t);
		if (oldDisplayName == null) return;
		container.remove(EnchantmentSolution.getKey(enchant.getName()));
		container.remove(EnchantmentSolution.getKey(enchant.getName() + "_level"));
		List<String> lore = meta.getLore();
		Iterator<String> iter = lore.iterator();
		while (iter.hasNext()) {
			String l = iter.next();
			String strip = ChatColor.stripColor(l);
			if (strip.startsWith(enchant.getDisplayName()) || strip.startsWith(oldDisplayName)) iter.remove();
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		setItemLore(item);
	}

	public static void setItemLore(ItemStack item) {
		String config = ConfigString.LORE_LOCATION.getString();
		if (config.equals("unset")) return;
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) lore = new ArrayList<String>();
		List<String> enchants = new ArrayList<String>();
		List<String> nonEnchants = new ArrayList<String>();
		for(String l: lore)
			if (isEnchantment(l)) enchants.add(l);
			else
				nonEnchants.add(l);
		if (config.equals("top")) {
			lore = new ArrayList<String>();
			for(String s: enchants)
				lore.add(s);
			for(String s: nonEnchants)
				lore.add(s);
		} else if (config.equals("bottom")) {
			lore = new ArrayList<String>();
			for(String s: nonEnchants)
				lore.add(s);
			for(String s: enchants)
				lore.add(s);
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public static String returnEnchantmentName(CustomEnchantment ench) {
		String displayName = ChatColor.GRAY + ench.getDisplayName();
		if (ench.isCurse()) displayName = ChatColor.RED + ench.getDisplayName();

		return displayName;
	}

	public static String returnEnchantmentName(EnchantmentLevel level) {
		return returnEnchantmentName(level.getEnchant(), level.getLevel());
	}

	public static String returnEnchantmentName(CustomEnchantment ench, int enchLevel) {
		String displayName = ChatColor.GRAY + ench.getDisplayName();
		if (ench.isCurse()) displayName = ChatColor.RED + ench.getDisplayName();
		if (enchLevel == 1 && ench.getMaxLevel() == 1) return displayName;
		if (enchLevel > 10 || enchLevel <= 0) return displayName + " enchantment.level." + enchLevel;

		return displayName + " " + NUMERALS[enchLevel - 1];
	}

	public static EnchantmentLevel returnEnchantmentLevel(String s, ItemMeta meta) {
		String[] pieces = s.split(" ");
		int level = 0;
		int repair = 0;
		if (pieces[pieces.length - 1].contains("enchantment.level")) {
			String[] enchLevel = pieces[pieces.length - 1].split(".");
			level = Integer.parseInt(enchLevel[enchLevel.length - 1]);
			repair = pieces.length - 1;
		} else {
			for(int i = 0; i < NUMERALS.length; i++)
				if (pieces[pieces.length - 1].equals(NUMERALS[i])) {
					level = i + 1;
					break;
				}
			if (level == 0) {
				level = 1;
				repair = pieces.length;
			} else
				repair = pieces.length - 1;
		}
		String repaired = pieces[0];
		for(int i = 1; i < repair; i++)
			repaired += " " + pieces[i];
		CustomEnchantment match = null;
		for(CustomEnchantment ench: RegisterEnchantments.getEnchantments())
			if (ench.getDisplayName().equals(repaired)) {
				if (meta.hasEnchant(ench.getRelativeEnchantment())) // already has the enchantment, so do nothing about it
					return null;
				match = ench;
			}

		if (match == null) // not an enchantment, so don't add one
			return null;

		return new EnchantmentLevel(match, level);
	}

	public static boolean isEnchantment(String s) {
		String[] pieces = s.split(" ");
		int level = 0;
		int repair = 0;
		if (pieces[pieces.length - 1].contains("enchantment.level")) {
			String[] enchLevel = pieces[pieces.length - 1].split(".");
			level = Integer.parseInt(enchLevel[enchLevel.length - 1]);
			repair = pieces.length - 1;
		} else {
			for(int i = 0; i < NUMERALS.length; i++)
				if (pieces[pieces.length - 1].equals(NUMERALS[i])) {
					level = i + 1;
					break;
				}
			if (level == 0) {
				level = 1;
				repair = pieces.length;
			} else
				repair = pieces.length - 1;
		}
		String repaired = pieces[0];
		for(int i = 1; i < repair; i++)
			repaired += " " + pieces[i];
		for(CustomEnchantment ench: RegisterEnchantments.getEnchantments())
			if (ench.getDisplayName().equals(repaired)) return true;
		return false;
	}

	public static String getEnchantmentString(EnchantmentLevel enchantment) {
		return ChatColor.GRAY + returnEnchantmentName(enchantment.getEnchant(), enchantment.getLevel());
	}

	public static ItemStack repairStickyHold(ItemStack stickyItem) {
		ItemStack item = stickyItem.clone();
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		MatData data = new MatData(container.get(EnchantmentSolution.getKey("sticky_hold_material"), t));
		if (data.hasMaterial()) {
			item = new ItemStack(data.getMaterial());
			item.setItemMeta(meta);
			ItemMeta newMeta = item.getItemMeta();
			container = newMeta.getPersistentDataContainer();
			container.remove(EnchantmentSolution.getKey("sticky_hold_material"));
			List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
			levels.add(new EnchantmentLevel(CERegister.STICKY_HOLD, 1));
			for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
				String s = container.get(EnchantmentSolution.getKey("sticky_hold_" + enchant.getName()), t);
				if (s != null) levels.add(new EnchantmentLevel(s));
				container.remove(EnchantmentSolution.getKey("sticky_hold_" + enchant.getName()));
			}
			item.setItemMeta(newMeta);
			item = ItemUtils.addEnchantmentsToItem(item, levels);
		}
		return item;
	}

	public static ItemStack createStickyHold(ItemStack item) {
		ItemStack stickyItem = item.clone();
		stickyItem.setType(Material.STICK);
		ItemMeta stickyMeta = stickyItem.getItemMeta();
		PersistentDataContainer container = stickyMeta.getPersistentDataContainer();

		container.set(EnchantmentSolution.getKey("sticky_hold_material"), t, item.getType().name());
		Iterator<Entry<Enchantment, Integer>> enchants = stickyMeta.getEnchants().entrySet().iterator();
		List<CustomEnchantment> remove = new ArrayList<CustomEnchantment>();

		while (enchants.hasNext()) {
			Entry<Enchantment, Integer> enchant = enchants.next();
			CustomEnchantment ench = RegisterEnchantments.getCustomEnchantment(enchant.getKey());
			if (!ench.equals(CERegister.STICKY_HOLD)) {
				remove.add(ench);
				container.set(EnchantmentSolution.getKey("sticky_hold_" + ench.getName()), t, new EnchantmentLevel(ench, enchant.getValue()).toString());
			}
		}
		stickyItem.setItemMeta(stickyMeta);
		for(CustomEnchantment e: remove)
			ItemUtils.removeEnchantmentFromItem(stickyItem, e);

		return stickyItem;
	}

	public static boolean isStickyHold(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		String s = container.get(EnchantmentSolution.getKey("sticky_hold_material"), t);
		return s != null && new MatData(s).hasMaterial();
	}

	public static Material stickyItemType(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		String s = container.get(EnchantmentSolution.getKey("sticky_hold_material"), t);
		return new MatData(s).getMaterial();
	}

	public static List<Integer> getAnimalIDsFromItem(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		List<Integer> ints = container.get(EnchantmentSolution.getKey("irenes_lasso_ids"), li);
		if (ints == null) ints = new ArrayList<Integer>();
		return ints;
	}

	public static boolean removeAnimal(ItemStack item, int id) {
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		List<Integer> ints = container.get(EnchantmentSolution.getKey("irenes_lasso_ids"), li);
		if (ints == null || ints.size() == 0) return false;
		Iterator<Integer> iter = ints.iterator();
		while (iter.hasNext()) {
			Integer i = iter.next();
			if (i == id) iter.remove();
		}
		container.set(EnchantmentSolution.getKey("irenes_lasso_ids"), li, ints);
		item.setItemMeta(meta);
		return true;
	}

	public static void addAnimal(ItemStack item, int id) {
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		List<Integer> ints = container.get(EnchantmentSolution.getKey("irenes_lasso_ids"), li);
		if (ints == null) ints = new ArrayList<Integer>();
		ints.add(id);
		container.set(EnchantmentSolution.getKey("irenes_lasso_ids"), li, ints);
		item.setItemMeta(meta);
	}
}
