package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;

public class EnchantmentLevel {

	private CustomEnchantment enchant;
	private int level;

	public EnchantmentLevel(CustomEnchantment enchant, int level) {
		this.enchant = enchant;
		this.level = level;
	}

	public EnchantmentLevel(String configString, EnchantmentErrorReason reason) {
		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%enchant%", configString);
		String[] split = configString.split(" ");
		if (split.length == 2) {
			try {
				level = Integer.parseInt(split[1]);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				return;
			}
			enchant = getEnchantFromString(split[0], Chatable.get().getMessage(reason.getReason(), codes));
		} else
			Chatable.get().sendWarning(Chatable.get().getMessage(reason.getReason(), codes));
	}

	public EnchantmentLevel(String configString, YamlConfig config) {
		String[] split = configString.split(" ");
		if (split.length == 2) {
			try {
				level = Integer.parseInt(split[1]);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				return;
			}
			enchant = getEnchantFromString(split[0], config);
		} else
			Chatable.get().sendWarning("Bad enchantment in configuration file " + config.getFileName() + ": " + configString + ". No chance to get this enchantment.");
	}

	private CustomEnchantment getEnchantFromString(String str, String error) {
		NamespacedKey key = null;
		String[] enchString = str.split("\\+");
		if (enchString[0].equalsIgnoreCase("minecraft")) key = NamespacedKey.minecraft(enchString[1]);
		else {
			Plugin plugin = null;
			for(Plugin pl: Bukkit.getPluginManager().getPlugins())
				if (pl.getName().equalsIgnoreCase(enchString[0])) {
					plugin = pl;
					break;
				}
			if (plugin != null) key = new NamespacedKey(plugin, enchString[1]);
		}
		if (Enchantment.getByKey(key) != null) for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments())
			if (enchantment.getRelativeEnchantment().equals(Enchantment.getByKey(key))) return enchantment;
		Chatable.get().sendWarning(error);
		return null;
	}

	private CustomEnchantment getEnchantFromString(String str, YamlConfig config) {
		NamespacedKey key = null;
		String[] enchString = str.split("\\+");
		if (enchString[0].equalsIgnoreCase("minecraft")) key = NamespacedKey.minecraft(enchString[1]);
		else {
			Plugin plugin = null;
			for(Plugin pl: Bukkit.getPluginManager().getPlugins())
				if (pl.getName().equalsIgnoreCase(enchString[0])) {
					plugin = pl;
					break;
				}
			if (plugin != null) key = new NamespacedKey(plugin, enchString[1]);
		}
		if (Enchantment.getByKey(key) != null) for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments())
			if (enchantment.getRelativeEnchantment().equals(Enchantment.getByKey(key))) return enchantment;
		Chatable.get().sendWarning("Bad enchantment in configuration file " + config.getFileName() + ": " + str + ". No chance to get this enchantment.");
		return null;
	}

	public CustomEnchantment getEnchant() {
		return enchant;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		NamespacedKey key = enchant.getRelativeEnchantment().getKey();
		return key.getNamespace() + "+" + key.getKey() + " " + level;
	}

	public static List<EnchantmentLevel> fromMap(Map<Enchantment, Integer> enchantmentList) {
		Iterator<Entry<Enchantment, Integer>> iter = enchantmentList.entrySet().iterator();
		List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
		while (iter.hasNext()) {
			Entry<Enchantment, Integer> entry = iter.next();
			levels.add(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(entry.getKey()), entry.getValue()));
		}
		return levels;
	}

	public static Map<Enchantment, Integer> fromList(List<EnchantmentLevel> levels) {
		Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
		for(EnchantmentLevel level: levels)
			map.put(level.getEnchant().getRelativeEnchantment(), level.getLevel());
		return map;
	}
}
