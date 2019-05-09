package org.ctp.enchantmentsolution.utils;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class PermissionUtils {

	public static boolean canEnchant(Player player, CustomEnchantment enchant, int level) {
		if(player == null) {
			return true;
		}
		ConfigFiles files = EnchantmentSolution.getPlugin().getConfigFiles();
		if(ConfigUtils.usePermissions()) {
			if(player.hasPermission("enchantmentsolution.permissions.ignore")) {
				return true;
			}
			for(int i = 0; i < level; i++) {
				if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
					JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
					if(plugin == null) {
						ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
								+ " does not have a JavaPlugin set. Refusing to check permissions.");
						return false;
					}
					String path = plugin.getName().toLowerCase() + "." + enchant.getName() + ".permissions.table.level" + (i + 1);
					if(files.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission(plugin.getName().toLowerCase() + "." + enchant.getName().toLowerCase() + ".table.level" + (i + 1))) {
							return false;
						}
					}
				} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
					String path = "custom_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1);
					if(files.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission("enchantmentsolution." + enchant.getName().toLowerCase() + ".table.level" + (i + 1))) {
							return false;
						}
					}
				} else {
					String path = "default_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1);
					if(files.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission("enchantmentsolution." + enchant.getName().toLowerCase() + ".table.level" + (i + 1))) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	public static boolean canAnvil(Player player, CustomEnchantment enchant, int level) {
		if(player == null) {
			return true;
		}
		ConfigFiles files = EnchantmentSolution.getPlugin().getConfigFiles();
		if(ConfigUtils.usePermissions()) {
			if(player.hasPermission("enchantmentsolution.permissions.ignore")) {
				return true;
			}
			for(int i = 0; i < level; i++) {
				if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
					JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
					if(plugin == null) {
						ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
								+ " does not have a JavaPlugin set. Refusing to check permissions.");
						return false;
					}
					String path = plugin.getName().toLowerCase() + "." + enchant.getName() + ".permissions.anvil.level" + (i + 1);
					if(files.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission(plugin.getName().toLowerCase() + "." + enchant.getName().toLowerCase() + ".anvil.level" + (i + 1))) {
							return false;
						}
					}
				} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
					String path = "custom_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1);
					if(files.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission("enchantmentsolution." + enchant.getName().toLowerCase() + ".anvil.level" + (i + 1))) {
							return false;
						}
					}
				} else {
					String path = "default_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1);
					if(files.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission("enchantmentsolution." + enchant.getName().toLowerCase() + ".anvil.level" + (i + 1))) {
							return false;
						}
					}
				}
			}
		}

		return true;
	}
}
