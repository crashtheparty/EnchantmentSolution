package org.ctp.enchantmentsolution.persistence;

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
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.*;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class PersistenceUtils {

	private static PersistentDataType<String, String> t = PersistentDataType.STRING;
	private static PersistentDataType<String, List<Integer>> li = new PersistentStringListInt();
	private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

	public static boolean addEnchantments(ItemStack item, List<EnchantmentLevel> levels) {
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
					oldLevel = Integer.parseInt(container.get(level.getEnchant().getPersistenceKey("_level"), t));
				} catch (Exception ex) {

				}
				container.set(level.getEnchant().getPersistenceKey(), t, ChatColor.translateAlternateColorCodes('&', level.getEnchant().getDisplayName()));
				container.set(level.getEnchant().getPersistenceKey("_level"), t, ((Integer) level.getLevel()).toString());
				String loreName = container.get(level.getEnchant().getRelativeEnchantment().getKey(), t);
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

	public static List<EnchantmentLevel> getEnchantments(ItemStack item) {
		List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
		if (item != null && item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			Map<Enchantment, Integer> vanilla = meta.getEnchants();
			if (item.getType() == Material.ENCHANTED_BOOK) vanilla = ((EnchantmentStorageMeta) meta).getStoredEnchants();
			for(Iterator<Entry<Enchantment, Integer>> it = vanilla.entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				CustomEnchantment ench = RegisterEnchantments.getCustomEnchantment(e.getKey());
				levels.add(new EnchantmentLevel(ench, e.getValue()));
			}
			PersistentDataContainer container = meta.getPersistentDataContainer();
			Iterator<NamespacedKey> iter = container.getKeys().iterator();
			while (iter.hasNext()) {
				NamespacedKey key = iter.next();
				EnchantmentWrapper wrapper = RegisterEnchantments.getByKey(key);
				if (wrapper != null && wrapper instanceof CustomEnchantmentWrapper) {
					CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(wrapper);
					int level = 0;
					try {
						level = Integer.parseInt(container.get(custom.getPersistenceKey("_level"), t));
					} catch (Exception ex) {

					}
					if (level > 0) levels.add(new EnchantmentLevel(custom, level));
				}
			}
		}
		return levels;
	}

	public static boolean hasEnchantment(ItemStack item, EnchantmentWrapper enchant) {
		if (item != null && item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			if (enchant instanceof CustomEnchantmentWrapper) {
				CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(enchant);
				PersistentDataContainer container = meta.getPersistentDataContainer();
				return container.get(custom.getPersistenceKey(), t) != null;
			}
			return item.containsEnchantment(enchant.getRelativeEnchantment());
		}
		return false;
	}

	public static EnchantmentLevel getEnchantmentLevel(ItemStack item, EnchantmentWrapper enchant) {
		return new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant), getLevel(item, enchant));
	}

	public static int getLevel(ItemStack item, EnchantmentWrapper enchant) {
		if (item != null && item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			if (enchant instanceof CustomEnchantmentWrapper) {
				int level = 0;
				CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(enchant);
				PersistentDataContainer container = meta.getPersistentDataContainer();
				if (container.get(custom.getPersistenceKey(), t) != null) try {
					level = Integer.parseInt(container.get(custom.getPersistenceKey("_level"), t));
				} catch (Exception ex) {}
				return level;
			}
			return item.getEnchantmentLevel(enchant.getRelativeEnchantment());
		}
		return 0;
	}

	public static ItemStack removeEnchantments(ItemStack item, CustomEnchantment enchant) {
		if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
			ItemMeta meta = item.getItemMeta();
			if (!meta.hasLore()) return item;
			PersistentDataContainer container = meta.getPersistentDataContainer();
			String oldDisplayName = container.get(enchant.getPersistenceKey(), t);
			if (oldDisplayName == null) oldDisplayName = "";
			container.remove(enchant.getPersistenceKey());
			container.remove(enchant.getPersistenceKey("_level"));
			List<String> lore = meta.getLore();
			Iterator<String> iter = lore.iterator();
			while (iter.hasNext()) {
				String l = iter.next();
				if (l.startsWith(ChatColor.translateAlternateColorCodes('&', enchant.getDisplayName()) + ChatColor.RESET) || l.startsWith(oldDisplayName)) iter.remove();
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			setItemLore(item);
		} else
			item.removeEnchantment(enchant.getRelativeEnchantment().getRelativeEnchantment());
		return item;
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
		String colorName = ChatColor.translateAlternateColorCodes('&', ench.getDisplayName());
		String displayName = ench.getDisplayName();
		if (!colorName.equals(displayName)) displayName = colorName;
		else {
			displayName = ChatColor.GRAY + ench.getDisplayName();
			if (ench.isCurse()) displayName = ChatColor.RED + ench.getDisplayName();
		}

		return displayName;
	}

	public static String returnEnchantmentName(EnchantmentLevel level) {
		return returnEnchantmentName(level.getEnchant(), level.getLevel());
	}

	public static String returnEnchantmentName(CustomEnchantment ench, int enchLevel) {
		String colorName = ChatColor.translateAlternateColorCodes('&', ench.getDisplayName());
		String displayName = ench.getDisplayName();
		if (!colorName.equals(displayName)) displayName = colorName;
		else {
			displayName = ChatColor.GRAY + ench.getDisplayName();
			if (ench.isCurse()) displayName = ChatColor.RED + ench.getDisplayName();
		}
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
			if (ench.getDisplayName().equals(repaired)) match = ench;

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
			NamespacedKey key = ench.getPersistenceKey();
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

	// public static boolean checkItem(ItemStack item) {
	// boolean changed = false;
	// if (item != null) {
	// List<EnchantmentLevel> enchantMeta = getEnchantments(item);
	// List<EnchantmentLevel> enchantLore = new ArrayList<EnchantmentLevel>();
	// if (item.hasItemMeta()) {
	// PersistentDataContainer container =
	// item.getItemMeta().getPersistentDataContainer();
	// for(NamespacedKey key: container.getKeys())
	// if (key.getNamespace().equalsIgnoreCase("enchantmentsolution")) {
	// Enchantment enchant = RegisterEnchantments.getByKey(key);
	// if (enchant != null) {
	// String enchantLevelString = container.get(new
	// NamespacedKey(EnchantmentSolution.getPlugin(), key.getKey() + "_level"),
	// PersistentDataType.STRING);
	// int enchantLevel = 0;
	// try {
	// enchantLevel = Integer.parseInt(enchantLevelString);
	// } catch (Exception ex) {}
	// enchantLore.add(new
	// EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant),
	// enchantLevel));
	// }
	// }
	// for(CustomEnchantment ench: RegisterEnchantments.getEnchantments()) {
	// String name = "ES_" + ench.getName();
	// int level = ItemNMS.getNBTData(item, name);
	// if (level > 0) enchantMeta.add(new EnchantmentLevel(ench, level));
	// if (!(ench.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) &&
	// EnchantmentUtils.hasEnchantment(item, ench.getRelativeEnchantment()))
	// enchantLore.add(new EnchantmentLevel(ench, level));
	// }
	// }
	// if (enchantMeta.size() == 0 && enchantLore.size() == 0) return false;
	//
	// boolean change = !checkSimilar(enchantMeta, enchantLore) &&
	// !checkSimilar(enchantLore, enchantMeta);
	// if (change) {
	// changed = true;
	// Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
	// for(EnchantmentLevel enchant: enchantMeta) {
	// int level = enchant.getLevel();
	// Enchantment ench = enchant.getEnchant().getRelativeEnchantment();
	// if (enchants.containsKey(ench)) level = level > enchants.get(ench) ? level :
	// enchants.get(ench);
	// enchants.put(ench, level);
	// }
	// for(EnchantmentLevel enchant: enchantLore) {
	// int level = enchant.getLevel();
	// Enchantment ench = enchant.getEnchant().getRelativeEnchantment();
	// if (enchants.containsKey(ench)) level = level > enchants.get(ench) ? level :
	// enchants.get(ench);
	// enchants.put(ench, level);
	// }
	// if (enchants.size() > 0) {
	// Iterator<Entry<Enchantment, Integer>> iter = enchants.entrySet().iterator();
	// while (iter.hasNext()) {
	// Entry<Enchantment, Integer> entry = iter.next();
	// CustomEnchantment custom =
	// RegisterEnchantments.getCustomEnchantment(entry.getKey());
	// EnchantmentUtils.removeEnchantmentFromItem(item, custom);
	// EnchantmentUtils.addEnchantmentToItem(item, custom, entry.getValue());
	// }
	// }
	// } else {
	// Iterator<EnchantmentLevel> iter = enchantMeta.iterator();
	// while (iter.hasNext()) {
	// EnchantmentLevel entry = iter.next();
	// if (PersistenceUtils.addPersistence(item, Arrays.asList(entry))) changed =
	// true;
	// }
	// }
	// }
	// return changed;
	// }
	//
	// private static boolean checkSimilar(List<EnchantmentLevel> levels,
	// List<EnchantmentLevel> levelsTwo) {
	// boolean similar = levels.size() == levelsTwo.size();
	// for(EnchantmentLevel level: levels) {
	// EnchantmentLevel hasLevel = null;
	// for(EnchantmentLevel levelTwo: levelsTwo)
	// if (level.getEnchant() != null && levelTwo.getEnchant() != null &&
	// level.getEnchant().getName().equals(levelTwo.getEnchant().getName()) &&
	// !(level.getEnchant() instanceof SnapshotEnchantment ^ levelTwo.getEnchant()
	// instanceof SnapshotEnchantment)) {
	// hasLevel = level;
	// break;
	// }
	// if (hasLevel == null) similar = false;
	// }
	// return similar;
	// }

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
