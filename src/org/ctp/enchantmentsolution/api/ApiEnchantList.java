package org.ctp.enchantmentsolution.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class ApiEnchantList {

	private static Map<JavaPlugin, List<ApiEnchantment>> ENCHANTMENTS = new LinkedHashMap<JavaPlugin, List<ApiEnchantment>>();
	
	public static void addEnchantment(JavaPlugin plugin, ApiEnchantment enchant) {
		List<ApiEnchantment> enchantments = ENCHANTMENTS.get(plugin);
		if (enchantments == null) {
			enchantments = new ArrayList<ApiEnchantment>();
		}
		if(!enchantments.contains(enchant)) {
			enchantments.add(enchant);
		}
		ENCHANTMENTS.put(plugin, enchantments);
	}
	
	public static void setEnchantments(JavaPlugin plugin) {
		List<ApiEnchantment> enchantments = ENCHANTMENTS.get(plugin);
		if(enchantments == null) {
			return;
		}
		
		for(ApiEnchantment enchant : enchantments) {
			DefaultEnchantments.addDefaultEnchantment(enchant);
		}
		
		ConfigFiles.updateExternalEnchantments(plugin);
		
		DefaultEnchantments.setEnchantments();
		
		ConfigFiles.updateEnchantments();
	}
	
	public static boolean isEnabled(Enchantment enchant) {
		return DefaultEnchantments.isEnabled(enchant);
	}
	
	public static boolean hasEnchantment(ItemStack item, Enchantment enchant) {
		return Enchantments.hasEnchantment(item, enchant);
	}
	
	public static ItemStack addEnchantmentsToItem(ItemStack item, List<EnchantmentLevel> levels) {
		return Enchantments.addEnchantmentsToItem(item, levels);
	}
	
}
