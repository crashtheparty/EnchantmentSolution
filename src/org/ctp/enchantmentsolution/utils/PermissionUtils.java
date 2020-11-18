package org.ctp.enchantmentsolution.utils;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;

public class PermissionUtils {

	private static Map<EnchantmentLevel, List<Permission>> PERMISSIONS = new HashMap<EnchantmentLevel, List<Permission>>();

	public static void addPermissions(EnchantmentLevel level) {
		List<Permission> permissions = getPermissions(level);
		permissions.add(new Permission("enchantmentsolution." + level.getEnchant().getName() + ".table.level" + level.getLevel(), PermissionDefault.FALSE));
		permissions.add(new Permission("enchantmentsolution." + level.getEnchant().getName() + ".anvil.level" + level.getLevel(), PermissionDefault.FALSE));
		for(Permission p: permissions)
			if (Bukkit.getPluginManager().getPermission(p.getName()) == null) Bukkit.getPluginManager().addPermission(p);
		PERMISSIONS.put(level, permissions);
	}

	public static void removePermissions(EnchantmentLevel level) {
		List<Permission> permissions = getPermissions(level);
		for(Permission p: permissions)
			Bukkit.getPluginManager().removePermission(p);
		PERMISSIONS.put(level, new ArrayList<Permission>());
	}

	private static List<Permission> getPermissions(EnchantmentLevel level) {
		Iterator<Entry<EnchantmentLevel, List<Permission>>> iterator = PERMISSIONS.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<EnchantmentLevel, List<Permission>> entry = iterator.next();
			if (entry.getKey().getEnchant().getRelativeEnchantment() == level.getEnchant().getRelativeEnchantment() && entry.getKey().getLevel() == level.getLevel()) return entry.getValue();
		}
		return new ArrayList<Permission>();
	}

	public static boolean canEnchant(Player player, CustomEnchantment enchant, int level) {
		if (player == null) return true;
		if (usePermissions()) {
			if (player.hasPermission("enchantmentsolution.permissions.ignore")) return true;
			String namespace = getNamespace(enchant);
			String path = namespace + "." + enchant.getName().toLowerCase(Locale.ROOT) + ".advanced.permissions.table.level";
			String permission = namespace + "." + enchant.getName().toLowerCase(Locale.ROOT) + ".table.level";
			return checkPermission(player, level, path, permission);
		}

		return true;
	}

	public static boolean canAnvil(Player player, CustomEnchantment enchant, int level) {
		if (player == null) return true;
		if (usePermissions()) {
			if (player.hasPermission("enchantmentsolution.permissions.ignore")) return true;
			String namespace = getNamespace(enchant);
			String path = namespace + "." + enchant.getName().toLowerCase(Locale.ROOT) + ".advanced.permissions.anvil.level";
			String permission = namespace + "." + enchant.getName().toLowerCase(Locale.ROOT) + ".anvil.level";
			return checkPermission(player, level, path, permission);
		}

		return true;
	}

	private static String getNamespace(CustomEnchantment enchant) {
		if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
			JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
			if (plugin == null) {
				Chatable.get().sendWarning("Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")" + " does not have a JavaPlugin set. Refusing to check permissions.");
				return null;
			}
			return plugin.getName().toLowerCase(Locale.ROOT);
		} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) return "custom_enchantments";
		else
			return "default_enchantments";
	}

	private static boolean checkPermission(Player player, int level, String path, String permission) {
		for(int i = 0; i < level; i++)
			if (Configurations.getConfigurations().getEnchantments().getBoolean(path + (i + 1))) if (!player.hasPermission(permission + (i + 1))) return false;
		return true;
	}

	private static boolean usePermissions() {
		return ConfigUtils.getAdvancedBoolean(ConfigString.USE_PERMISSIONS, false);
	}

	public static boolean check(Player player, String... permissions) {
		for(String s: permissions)
			if (player.hasPermission(s)) return true;
		return false;
	}
}
