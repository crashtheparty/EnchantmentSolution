package org.ctp.enchantmentsolution.utils;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;

public class PermissionUtils {

	public static boolean canEnchant(Player player, CustomEnchantment enchant, int level) {
		if (player == null) {
			return true;
		}
		if (usePermissions()) {
			if (player.hasPermission("enchantmentsolution.permissions.ignore")) {
				return true;
			}
			String namespace = getNamespace(enchant);
			String path = namespace + "." + enchant.getName().toLowerCase() + ".permissions.table.level";
			String permission = namespace + "." + enchant.getName().toLowerCase() + ".table.level";
			return checkPermission(player, level, path, permission);
		}

		return true;
	}

	public static boolean canAnvil(Player player, CustomEnchantment enchant, int level) {
		if (player == null) {
			return true;
		}
		if (usePermissions()) {
			if (player.hasPermission("enchantmentsolution.permissions.ignore")) {
				return true;
			}
			String namespace = getNamespace(enchant);
			String path = namespace + "." + enchant.getName().toLowerCase() + ".permissions.anvil.level";
			String permission = namespace + "." + enchant.getName().toLowerCase() + ".anvil.level";
			return checkPermission(player, level, path, permission);
		}

		return true;
	}

	private static String getNamespace(CustomEnchantment enchant) {
		if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
			JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
			if (plugin == null) {
				ChatUtils.sendToConsole(Level.WARNING,
				"Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
				+ " does not have a JavaPlugin set. Refusing to check permissions.");
				return null;
			}
			return plugin.getName().toLowerCase();
		} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
			return "custom_enchantments";
		} else {
			return "default_enchantments";
		}
	}

	private static boolean checkPermission(Player player, int level, String path, String permission) {
		for(int i = 0; i < level; i++) {
			if (Configurations.getEnchantments().getBoolean(path + (i + 1))) {
				if (!player.hasPermission(permission + (i + 1))) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean usePermissions() {
		return ConfigUtils.getAdvancedBoolean(ConfigString.USE_PERMISSIONS, false);
	}
}
