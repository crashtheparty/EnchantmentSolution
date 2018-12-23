package org.ctp.enchantmentsolution.utils;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class PermissionUtils {

	public static boolean canEnchant(Player player, CustomEnchantment enchant, int level) {
		if(player == null) {
			return true;
		}
		if(ConfigFiles.usePermissions()) {
			if(player.hasPermission("enchantmentsolution.permissions.ignore")) {
				return true;
			}
			for(int i = 0; i < level; i++) {
				if(enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
					String path = "custom_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1);
					if(ConfigFiles.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission("enchantmentsolution." + enchant.getName() + ".table.level" + (i + 1))) {
							return false;
						}
					}
				} else {
					String path = "default_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1);
					if(ConfigFiles.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission("enchantmentsolution." + enchant.getName() + ".table.level" + (i + 1))) {
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
		if(ConfigFiles.usePermissions()) {
			if(player.hasPermission("enchantmentsolution.permissions.ignore")) {
				return true;
			}
			for(int i = 0; i < level; i++) {
				if(enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
					String path = "custom_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1);
					if(ConfigFiles.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission("enchantmentsolution." + enchant.getName() + ".anvil.level" + (i + 1))) {
							return false;
						}
					}
				} else {
					String path = "default_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1);
					if(ConfigFiles.getEnchantmentAdvancedConfig().getBoolean(path)) {
						if(!player.hasPermission("enchantmentsolution." + enchant.getName() + ".anvil.level" + (i + 1))) {
							return false;
						}
					}
				}
			}
		}

		return true;
	}
}
