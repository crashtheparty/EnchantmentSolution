package org.ctp.enchantmentsolution.nms.persistence;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.*;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentErrorReason;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class PersistenceUtils {

	private static PersistentDataType<String, String> t = PersistentDataType.STRING;
	private static PersistentDataType<String, List<Integer>> li = new PersistentStringListInt();
	private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

	public static boolean addPersistence(ItemStack item, List<EnchantmentLevel> levels) {
		boolean changed = false;
		ItemMeta meta = item.getItemMeta();
		if (meta.getLore() == null) meta.setLore(new ArrayList<String>());
		PersistentDataContainer container = meta.getPersistentDataContainer();
		List<String> lore = meta.getLore();
		if (lore == null) lore = new ArrayList<String>();
		for(EnchantmentLevel level: levels)
			if (level.getEnchant().getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				int oldLevel = 0;
				try {
					oldLevel = Integer.parseInt(container.get(EnchantmentSolution.getKey(level.getEnchant().getName() + "_level"), t));
				} catch (Exception ex) {

				}
				container.set(EnchantmentSolution.getKey(level.getEnchant().getName()), t, level.getEnchant().getDisplayName());
				container.set(EnchantmentSolution.getKey(level.getEnchant().getName() + "_level"), t, ((Integer) level.getLevel()).toString());
				String loreName = container.get(EnchantmentSolution.getKey(level.getEnchant().getName()), t);
				Iterator<String> iter = lore.iterator();
				boolean hadLore = false;
				while (iter.hasNext()) {
					String l = iter.next();
					if (ChatColor.stripColor(l).startsWith(loreName)) {
						if (oldLevel != level.getLevel()) changed = true;
						hadLore = true;
						iter.remove();
					}
				}
				if (!hadLore) changed = true;
				lore.add(returnEnchantmentName(level));
			}
		meta.setLore(lore);
		item.setItemMeta(meta);
		if (setItemLore(item)) changed = true;
		return changed;
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

	public static boolean setItemLore(ItemStack item) {
		boolean changed = false;
		String config = ConfigString.LORE_LOCATION.getString();
		if (config.equals("unset")) return false;
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) return false;
		List<String> newLore = new ArrayList<String>();
		if (config.equalsIgnoreCase("top")) {
			for(String s: lore) {
				EnchResult result = isEnchantment(meta, s);
				if (result.isEnchant() && result.isChange()) {
					newLore.add(returnEnchantmentName(result.getEnchantment(), EnchantmentUtils.getLevel(item, result.getEnchantment().getRelativeEnchantment())));
					changed = true;
				} else if (result.isEnchant()) newLore.add(s);
			}
			for(String s: lore) {
				EnchResult result = isEnchantment(meta, s);
				if (!result.isEnchant()) newLore.add(s);
			}
		} else if (config.equalsIgnoreCase("bottom")) {
			for(String s: lore) {
				EnchResult result = isEnchantment(meta, s);
				if (!result.isEnchant()) newLore.add(s);
			}
			for(String s: lore) {
				EnchResult result = isEnchantment(meta, s);
				if (result.isEnchant() && result.isChange()) {
					newLore.add(returnEnchantmentName(result.getEnchantment(), EnchantmentUtils.getLevel(item, result.getEnchantment().getRelativeEnchantment())));
					changed = true;
				} else if (result.isEnchant()) newLore.add(s);
			}
		} else
			newLore = lore;
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return changed;
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
			String[] enchLevel = pieces[pieces.length - 1].split("\\.");
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

	public static EnchResult isEnchantment(ItemMeta meta, String s) {
		s = ChatColor.stripColor(s);
		String[] pieces = s.split(" ");
		if (pieces.length == 0) return new EnchResult(false, false, null);
		int level = 0;
		int repair = 0;
		if (pieces[pieces.length - 1].contains("enchantment.level")) {
			String[] enchLevel = pieces[pieces.length - 1].split(Pattern.quote("."));
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
		PersistentDataContainer container = meta.getPersistentDataContainer();
		for(CustomEnchantment ench: RegisterEnchantments.getEnchantments()) {
			NamespacedKey key = EnchantmentSolution.getKey(ench.getName());
			if (container.has(key, t)) {
				if (ench.getDisplayName().trim().equals(repaired.trim())) return new EnchResult(false, true, ench);
				String oldDisplayName = container.get(key, t);
				if (oldDisplayName != null && oldDisplayName.equals(repaired)) return new EnchResult(true, true, ench);
			}
		}
		return new EnchResult(false, false, null);
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
				if (s != null) levels.add(new EnchantmentLevel(s, EnchantmentErrorReason.STICKY_HOLD));
				container.remove(EnchantmentSolution.getKey("sticky_hold_" + enchant.getName()));
			}
			item.setItemMeta(newMeta);
			item = EnchantmentUtils.addEnchantmentsToItem(item, levels);
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
			EnchantmentUtils.removeEnchantmentFromItem(stickyItem, e);

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

	public static boolean checkItem(ItemStack item) {
		boolean changed = false;
		if (item != null) {
			List<EnchantmentLevel> enchantMeta = getEnchantments(item);
			List<EnchantmentLevel> enchantLore = new ArrayList<EnchantmentLevel>();
			if (item.hasItemMeta()) {
				PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
				for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments())
					if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
						NamespacedKey key = EnchantmentSolution.getKey(enchant.getName());
						NamespacedKey keyLevel = EnchantmentSolution.getKey(enchant.getName() + "_level");
						if (container.get(key, t) != null && container.get(keyLevel, t) != null) enchantLore.add(new EnchantmentLevel(enchant, Integer.parseInt(container.get(keyLevel, t))));
					} else if (item.containsEnchantment(enchant.getRelativeEnchantment())) enchantLore.add(new EnchantmentLevel(enchant, EnchantmentUtils.getLevel(item, enchant.getRelativeEnchantment())));
			}

			boolean change = !checkSimilar(enchantMeta, enchantLore) && !checkSimilar(enchantLore, enchantMeta);
			if (change) {
				changed = true;
				Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
				for(EnchantmentLevel enchant: enchantMeta) {
					int level = enchant.getLevel();
					Enchantment ench = enchant.getEnchant().getRelativeEnchantment();
					if (enchants.containsKey(ench)) level = level > enchants.get(ench) ? level : enchants.get(ench);
					enchants.put(ench, level);
				}
				for(EnchantmentLevel enchant: enchantLore) {
					int level = enchant.getLevel();
					Enchantment ench = enchant.getEnchant().getRelativeEnchantment();
					if (enchants.containsKey(ench)) level = level > enchants.get(ench) ? level : enchants.get(ench);
					enchants.put(ench, level);
				}
				if (enchants.size() > 0) {
					Iterator<Entry<Enchantment, Integer>> iter = enchants.entrySet().iterator();
					while (iter.hasNext()) {
						Entry<Enchantment, Integer> entry = iter.next();
						CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(entry.getKey());
						EnchantmentUtils.removeEnchantmentFromItem(item, custom);
						EnchantmentUtils.addEnchantmentToItem(item, custom, entry.getValue());
					}
				}
			} else {
				Iterator<EnchantmentLevel> iter = enchantMeta.iterator();
				while (iter.hasNext()) {
					EnchantmentLevel entry = iter.next();
					if (PersistenceNMS.addEnchantment(item, entry)) changed = true;
				}
			}
		}
		return changed;
	}

	private static boolean checkSimilar(List<EnchantmentLevel> levels, List<EnchantmentLevel> levelsTwo) {
		boolean similar = true;
		for(EnchantmentLevel level: levels) {
			EnchantmentLevel hasLevel = null;
			for(EnchantmentLevel levelTwo: levelsTwo)
				if (level.getEnchant().getName().equals(levelTwo.getEnchant().getName()) && !(level.getEnchant() instanceof SnapshotEnchantment ^ levelTwo.getEnchant() instanceof SnapshotEnchantment)) {
					hasLevel = level;
					break;
				}
			if (hasLevel == null) similar = false;
		}
		return similar;
	}

	private static List<EnchantmentLevel> getEnchantments(ItemStack item) {
		List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
		if (item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			Map<Enchantment, Integer> enchantments = meta.getEnchants();
			if (item.getType() == Material.ENCHANTED_BOOK) enchantments = ((EnchantmentStorageMeta) meta).getStoredEnchants();
			for(Iterator<Entry<Enchantment, Integer>> it = enchantments.entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				CustomEnchantment ench = RegisterEnchantments.getCustomEnchantment(e.getKey());
				if (ench == null) ench = new SnapshotEnchantment(e.getKey());
				levels.add(new EnchantmentLevel(ench, e.getValue()));
			}
		}
		return levels;
	}

	public static class EnchResult {
		private final boolean enchant, change;
		private final CustomEnchantment enchantment;

		EnchResult(boolean change, boolean enchant, CustomEnchantment enchantment) {
			this.enchantment = enchantment;
			this.enchant = enchant;
			this.change = change;
		}

		public CustomEnchantment getEnchantment() {
			return enchantment;
		}

		public boolean isEnchant() {
			return enchant;
		}

		public boolean isChange() {
			return change;
		}
	}
}
