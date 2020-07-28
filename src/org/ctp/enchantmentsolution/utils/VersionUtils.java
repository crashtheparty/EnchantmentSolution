package org.ctp.enchantmentsolution.utils;

import org.ctp.crashapi.CrashAPI;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class VersionUtils {

	public static int getBukkitVersionNumber() {
		return CrashAPI.getPlugin().getBukkitVersion().getVersionNumber();
	}

	public static String getMcMMOType() {
		return EnchantmentSolution.getPlugin().getMcMMOType();
	}

}
