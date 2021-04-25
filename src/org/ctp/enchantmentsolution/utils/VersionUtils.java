package org.ctp.enchantmentsolution.utils;

import org.ctp.crashapi.CrashAPI;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class VersionUtils {

	public static String getESVersionName() {
		return EnchantmentSolution.getPlugin().getPluginVersion().getCurrent().getVersionName();
	}

	public static int getVersionNumber() {
		int version = CrashAPI.getPlugin().getBukkitVersion().getVersionNumber();
		if (version < 3) version = 4;
		return version;
	}

	public static String getMinecraftAPIVersion() {
		return CrashAPI.getPlugin().getBukkitVersion().getAPIVersion();
	}

	public static String getMcMMOType() {
		return EnchantmentSolution.getPlugin().getMcMMOType();
	}

	public static String getMinecraftVersion() {
		return CrashAPI.getPlugin().getBukkitVersion().getVersion();
	}

}
