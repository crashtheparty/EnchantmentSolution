package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.MatData;

public class StringUtils {

	private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

	public static String returnEnchantmentName(CustomEnchantment ench) {
		String displayName = ench.getDisplayName();
		if (ench.isCurse()) displayName = ChatColor.RED + displayName;

		return displayName;
	}

	public static String returnEnchantmentName(CustomEnchantment ench, int enchLevel) {
		String displayName = ench.getDisplayName();
		if (ench.isCurse()) displayName = ChatColor.RED + displayName;
		if (enchLevel == 1 && ench.getMaxLevel() == 1) return displayName;
		if (enchLevel > 10 || enchLevel <= 0) return displayName + " enchantment.level." + enchLevel;

		return displayName + " " + NUMERALS[enchLevel - 1];
	}
	
	public static String stickyHoldItemType(ItemStack item) {
		return ChatColor.ITALIC + "" + ChatColor.ITALIC + ChatUtils.hideText(item.getType().name()) + ChatColor.RESET + ChatColor.GRAY + item.getItemMeta().getDisplayName();
	}

	public static ItemStack stickyHoldItem(ItemMeta itemMeta) {
		ItemStack item = null;
		for(String l : itemMeta.getLore()) {
			item = stickyHoldItem(l, itemMeta);
			if(item != null) break;
		}
		return item;
	}
	
	public static ItemStack stickyHoldItem(String s, ItemMeta meta) {
		if(s.startsWith(ChatColor.ITALIC + "" + ChatColor.ITALIC)) {
			s = s.replaceAll(ChatColor.ITALIC + "", "");
			MatData itemType = new MatData(ChatUtils.revealText(s.substring(0, s.indexOf(ChatColor.RESET + ""))));
			if(itemType.hasMaterial()) {
				ItemStack item = new ItemStack(itemType.getMaterial());
				item.setItemMeta(meta);
				return item;
			}
		}
		return null;
	}

	public static String addStickyHold(EnchantmentLevel level) {
		return ChatColor.UNDERLINE + "" + ChatColor.UNDERLINE + getEnchantmentString(level);
	}

	public static EnchantmentLevel stickyHoldLevel(String s) {
		if (s.indexOf(ChatColor.UNDERLINE + "" + ChatColor.UNDERLINE) == 0) {
			s = s.replaceAll(ChatColor.UNDERLINE + "", "");
			String enchHidden = s.substring(0, s.indexOf(ChatColor.RESET + ""));
			String enchName = ChatUtils.revealText(enchHidden);
			CustomEnchantment enchant = RegisterEnchantments.getByName(enchName);
			s = s.substring(s.indexOf(ChatColor.RESET + "") + 2);
			int level = 0;
			if (s.indexOf(ChatColor.RESET + "") == -1) level = 0;
			else {
				String levelHidden = s.substring(0, s.indexOf(ChatColor.RESET + ""));
				try {
					level = Integer.parseInt(ChatUtils.revealText(levelHidden));
				} catch (NumberFormatException ex) {

				}
			}

			return level > 0 && enchant != null ? new EnchantmentLevel(enchant, level) : null;
		}
		return null;
	}

	public static EnchantmentLevel returnEnchantmentLevel(String s, ItemMeta meta) {
		String[] pieces = s.split(" ");
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

	public static boolean isRetroEnchantment(String s) {
		if (s.startsWith(ChatUtils.hideText("legacy") + "" + ChatColor.GRAY + "" + ChatColor.BLUE)) return false;
		if (s.startsWith(ChatUtils.hideText("legacy") + "" + ChatColor.GRAY)) return true;
		return false;
	}

	public static boolean isLegacyEnchantment(String s) {
		if (s.startsWith(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + "" + ChatColor.BLUE)) return false;
		if (s.startsWith(ChatUtils.hideText("solution") + "" + ChatColor.GRAY)) return true;
		s = ChatColor.stripColor(s);
		String[] pieces = s.split(" ");
		if (pieces.length == 0) return false;
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
		for(CustomEnchantment ench: RegisterEnchantments.getEnchantments())
			if (ench.getDisplayName().equals(repaired)) return true;
		return false;
	}

	public static boolean isEnchantment(String s) {
		if (s.indexOf(ChatColor.ITALIC + "" + ChatColor.ITALIC) == 0 || s.indexOf(ChatColor.UNDERLINE + "" + ChatColor.UNDERLINE) == 0) return false;
		if (s.indexOf(ChatColor.RESET + "") > -1) {
			String name = s.substring(0, s.indexOf(ChatColor.RESET + ""));
			if (ChatColor.stripColor(name).equals("")) {
				String enchName = ChatUtils.revealText(name);
				if (RegisterEnchantments.getByName(enchName) != null) return true;
			}
		}
		return isLegacyEnchantment(s);
	}

	public static String getEnchantmentString(EnchantmentLevel enchantment) {
		return ChatUtils.hideText(enchantment.getEnchant().getName()) + ChatColor.RESET + ChatUtils.hideText(enchantment.getLevel() + "") + ChatColor.RESET + ChatColor.GRAY + returnEnchantmentName(enchantment.getEnchant(), enchantment.getLevel());
	}

	public static EnchantmentLevel getEnchantment(String s) {
		if (s.indexOf(ChatColor.ITALIC + "" + ChatColor.ITALIC) == 0 || s.indexOf(ChatColor.UNDERLINE + "" + ChatColor.UNDERLINE) == 0) return null;
		if (s.indexOf(ChatColor.RESET + "") > -1) {
			int level = 0;
			CustomEnchantment enchant = null;
			try {
				String enchHidden = s.substring(0, s.indexOf(ChatColor.RESET + ""));
				String enchName = ChatUtils.revealText(enchHidden);
				enchant = RegisterEnchantments.getByName(enchName);
				s = s.substring(s.indexOf(ChatColor.RESET + "") + 2);
				level = 0;
				if (s.indexOf(ChatColor.RESET + "") == -1) level = 0;
				else {
					String levelHidden = s.substring(0, s.indexOf(ChatColor.RESET + ""));
					try {
						level = Integer.parseInt(ChatUtils.revealText(levelHidden));
					} catch (NumberFormatException ex) {
	
					}
				}

			} catch (Exception ex) {}
			return level > 0 && enchant != null ? new EnchantmentLevel(enchant, level) : null;
		}
		return null;
	}

	public static void addAnimal(ItemStack item, int entityID) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) lore = new ArrayList<String>();
		lore.add(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + "" + ChatColor.BLUE + "Entity ID: " + entityID);
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public static List<Integer> getAnimalIDsFromItem(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		List<Integer> ids = new ArrayList<Integer>();
		if (lore == null) lore = new ArrayList<String>();
		for(String l: lore)
			if (l.startsWith(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + "" + ChatColor.BLUE)) try {
				ids.add(Integer.parseInt(l.substring(l.indexOf("Entity ID: ") + "Entity ID: ".length())));
			} catch (Exception ex) {}
		return ids;
	}

	public static void removeAnimal(ItemStack item, int entityID) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) lore = new ArrayList<String>();
		while (lore.contains(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + "" + ChatColor.BLUE + "Entity ID: " + entityID))
			lore.remove(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + "" + ChatColor.BLUE + "Entity ID: " + entityID);
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public static List<String> removeEnchantment(CustomEnchantment enchantment, List<String> lore) {
		if (lore == null) return lore;
		Iterator<String> iterator = lore.iterator();
		while (iterator.hasNext()) {
			String l = iterator.next();
			EnchantmentLevel level = getEnchantment(l);
			if (level != null && level.getEnchant().equals(enchantment)) iterator.remove();
		}
		return lore;
	}

	public static List<String> removeEnchantment(CustomEnchantment enchantment, int level, List<String> lore) {
		if (lore == null) return lore;
		Iterator<String> iterator = lore.iterator();
		while (iterator.hasNext()) {
			String l = iterator.next();
			if (l.endsWith(returnEnchantmentName(enchantment, level))) iterator.remove();
		}
		return lore;
	}

	public static String encodeString(String st) {
		String regex = "\\n";
		String regex2 = "\"";

		return st.replaceAll(regex, "\\\\n").replaceAll(regex2, "\\\"");
	}

	public static String decodeString(String st) {
		if (st == null) return st;
		StringBuilder sb = new StringBuilder(st.length());

		for(int i = 0; i < st.length(); i++) {
			char ch = st.charAt(i);
			if (ch == '\\') {
				char nextChar = i == st.length() - 1 ? '\\' : st.charAt(i + 1);
				// Octal escape?
				if (nextChar >= '0' && nextChar <= '7') {
					String code = "" + nextChar;
					i++;
					if (i < st.length() - 1 && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
						code += st.charAt(i + 1);
						i++;
						if (i < st.length() - 1 && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
							code += st.charAt(i + 1);
							i++;
						}
					}
					sb.append((char) Integer.parseInt(code, 8));
					continue;
				}
				switch (nextChar) {
					case '\\':
						ch = '\\';
						break;
					case 'b':
						ch = '\b';
						break;
					case 'f':
						ch = '\f';
						break;
					case 'n':
						ch = '\n';
						break;
					case 'r':
						ch = '\r';
						break;
					case 't':
						ch = '\t';
						break;
					case '\"':
						ch = '\"';
						break;
					case '\'':
						ch = '\'';
						break;
				}
				i++;
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	public static String join(List<String> strings, String divider) {
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < strings.size(); i++) {
			sb.append(strings.get(i));
			if (i < strings.size() - 1) sb.append(divider);
		}

		return sb.toString();
	}
}
