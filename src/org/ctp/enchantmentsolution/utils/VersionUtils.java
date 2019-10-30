package org.ctp.enchantmentsolution.utils;

import org.ctp.enchantmentsolution.EnchantmentSolution;

public class VersionUtils {

	public static int getBukkitVersionNumber() {
		return EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber();
	}
	
	public static String getMcMMOType() {
		return EnchantmentSolution.getPlugin().getMcMMOType();
	}

}
