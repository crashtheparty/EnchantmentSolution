package org.ctp.enchantmentsolution.enchantments.mcmmo;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;

class FishingEnchanted {

	private CustomEnchantment enchant;
	private int level;
	
	FishingEnchanted(String configString) {
		String[] split = configString.split(" ");
		if(split.length == 2) {
			try {
				this.level = Integer.parseInt(split[1]);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				return;
			}
			this.enchant = getEnchantFromString(split[0]);
		} else {
			ChatUtils.sendToConsole(Level.WARNING, "Bad enchantment in fishing config: " + configString + ". No chance to get this enchantment.");
		}
	}
	
	private CustomEnchantment getEnchantFromString(String str) {
		NamespacedKey key = null;
		String[] enchString = str.split("\\+");
		if(enchString[0].equalsIgnoreCase("minecraft")) {
			key = NamespacedKey.minecraft(enchString[1]);
		} else {
			Plugin plugin = null;
			for(Plugin pl : Bukkit.getPluginManager().getPlugins()) {
				if(pl.getName().equalsIgnoreCase(enchString[0])) {
					plugin = pl;
					break;
				}
			}
			if(plugin != null) {
				key = new NamespacedKey(plugin, enchString[1]);
			}
		}
		if (Enchantment.getByKey(key) != null) {
			for(CustomEnchantment enchantment : DefaultEnchantments.getEnchantments()) {
				if(enchantment.getRelativeEnchantment().equals(Enchantment.getByKey(key))) {
					return enchantment;
				}
			}
		}
		ChatUtils.sendToConsole(Level.WARNING, "Bad enchantment in fishing config: " + str + ". No chance to get this enchantment.");
		return null;
	}

	public CustomEnchantment getEnchant() {
		return enchant;
	}

	public int getLevel() {
		return level;
	}
	
	public String toString() {
		if(enchant == null) {
			return "null";
		}
		
		Enchantment enchant = this.enchant.getRelativeEnchantment();
		
		if(enchant == null) {
			return "null";
		}
		
		return enchant.getKey().getNamespace() + "+" + enchant.getKey().getKey() + " " + level;
	}
}
